package capatan.kurt.nbateamviewer.ui

import androidx.lifecycle.*
import capatan.kurt.nbateamviewer.core.DispatcherProvider
import capatan.kurt.nbateamviewer.datasource.Team
import capatan.kurt.nbateamviewer.core.Result
import capatan.kurt.nbateamviewer.repository.TeamRepository
import kotlinx.coroutines.*

class MainViewModel(private val teamRepository: TeamRepository,
                    private val dispatcherProvider: DispatcherProvider = DispatcherProvider()): ViewModel() {

    private val mutableResultLiveData = MutableLiveData<Result<List<Team>>>()
    private var teamSortMethod: SortMethod = SortMethod.Name()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        mutableResultLiveData.postValue(Result.Failure(throwable))
    }

    val resultLiveData: LiveData<Result<List<Team>>> = mutableResultLiveData


    fun refreshTeams() {
        mutableResultLiveData.value = Result.Loading

        viewModelScope.launch(dispatcherProvider.IO + exceptionHandler) {

            val teams = teamRepository.getNbaTeams()
            val sortedTeams = sortTeamList(teamSortMethod, teams)

            withContext(dispatcherProvider.Main) {
                mutableResultLiveData.postValue(Result.Success(sortedTeams)) }
            }
    }

    fun sortByTeamName(ascending: Boolean = true) = updateTeamsSortedState(SortMethod.Name(ascending))

    fun sortByWins(ascending:Boolean = true) = updateTeamsSortedState(SortMethod.Win(ascending))

    fun sortByLosses(ascending: Boolean = true) = updateTeamsSortedState(SortMethod.Loss(ascending))


    private fun updateTeamsSortedState(sortMethod: SortMethod) {
        teamSortMethod = sortMethod

        viewModelScope.launch(dispatcherProvider.Default) {
            val result = mutableResultLiveData.value

            if (result != null && result is Result.Success) {
                val sorted = sortTeamList(sortMethod, result.data)

                withContext(dispatcherProvider.Main) {
                    mutableResultLiveData.postValue(Result.Success(sorted))
                }
            }
        }
    }

    private fun sortTeamList(sortMethod: SortMethod, teams: List<Team>): List<Team> {
        return when (sortMethod) {
            is SortMethod.Name -> teamRepository.sortTeamsByName(sortMethod.ascending, teams)
            is SortMethod.Win -> teamRepository.sortTeamsByWins(sortMethod.ascending, teams)
            is SortMethod.Loss -> teamRepository.sortTeamsByLosses(sortMethod.ascending, teams)
        }
    }

    private sealed class SortMethod {
        data class Name(val ascending: Boolean = true): SortMethod()
        data class Win(val ascending: Boolean = true): SortMethod()
        data class Loss(val ascending: Boolean = true): SortMethod()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val teamRepository: TeamRepository): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                MainViewModel(teamRepository) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

}