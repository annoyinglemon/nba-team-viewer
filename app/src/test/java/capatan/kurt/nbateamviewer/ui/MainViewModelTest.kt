package capatan.kurt.nbateamviewer.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import capatan.kurt.nbateamviewer.core.*
import capatan.kurt.nbateamviewer.datasource.Team
import capatan.kurt.nbateamviewer.repository.TeamRepository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Matchers.*
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesRule = CoroutinesTestRule()

    private lateinit var teamRepository: TeamRepository
    private lateinit var resultObserver: Observer<Result<List<Team>>>
    private lateinit var resultCaptor: MutableList<Result<List<Team>>>

    private lateinit var mainViewModel: MainViewModel

    private val testDispatcherProvider =  TestDispatcherProvider(coroutinesRule.testDispatcher)

    @Before
    fun setUp() {
        teamRepository = spyk(TeamRepository(mockk()))
        resultObserver = mockk()
        resultCaptor = mutableListOf()
        every { resultObserver.onChanged(capture(resultCaptor)) } answers {}

        mainViewModel = MainViewModel(teamRepository, testDispatcherProvider)

        mainViewModel.resultLiveData.observeForever(resultObserver)
    }

    @Test
    fun refreshTeams_success() = coroutinesRule.testDispatcher.runBlockingTest {
        coEvery { teamRepository.getNbaTeams() } returns NBA_TEAMS

        mainViewModel.refreshTeams()

        assertThat(resultCaptor.size, `is`(equalTo(2)))
        assertThat(resultCaptor[0], `is`(instanceOf(Result.Loading::class.java)))
        assertThat(resultCaptor[1], `is`(instanceOf(Result.Success::class.java)))

        val resultTeams = (resultCaptor[1] as Result.Success).data
        assertThat(resultTeams, `is`(equalTo(NBA_TEAMS)))

        coVerify(exactly = 1) {
            teamRepository.getNbaTeams()
        }

        verify(exactly = 1) {
            teamRepository.sortTeamsByName(true, NBA_TEAMS)
        }
    }

    @Test
    fun refreshTeams_fails() = coroutinesRule.testDispatcher.runBlockingTest {
        val errorMessage = randomString()
        val fakeException = Exception(errorMessage)

        every { resultObserver.onChanged(capture(resultCaptor)) } answers {}

        coEvery { teamRepository.getNbaTeams() } throws fakeException

        mainViewModel.refreshTeams()

        assertThat(resultCaptor.size, `is`(equalTo(2)))
        assertThat(resultCaptor[0], `is`(instanceOf(Result.Loading::class.java)))
        assertThat(resultCaptor[1], `is`(instanceOf(Result.Failure::class.java)))

        val resultThrowable = (resultCaptor[1] as Result.Failure).throwable
        assertThat(resultThrowable.message, `is`(equalTo(errorMessage)))

        coVerify(exactly = 1) {
            teamRepository.getNbaTeams()
        }
    }

    @Test
    fun refreshTeamList_then_sortByTeamName_ascending() = coroutinesRule.testDispatcher.runBlockingTest {
        coEvery { teamRepository.getNbaTeams() } returns NBA_TEAMS

        mainViewModel.refreshTeams()

        assertThat(resultCaptor.size, `is`(equalTo(2)))
        assertThat(resultCaptor[0], `is`(instanceOf(Result.Loading::class.java)))
        assertThat(resultCaptor[1], `is`(instanceOf(Result.Success::class.java)))

        val resultTeams = (resultCaptor[1] as Result.Success).data
        assertThat(resultTeams, `is`(equalTo(NBA_TEAMS)))

        coVerify(exactly = 1) {
            teamRepository.getNbaTeams()
        }

        mainViewModel.sortByTeamName(true)

        assertThat(resultCaptor.size, `is`(equalTo(3)))
        assertThat(resultCaptor[2], `is`(instanceOf(Result.Success::class.java)))

        val sortedTeam = (resultCaptor[2] as Result.Success).data
        assertThat(sortedTeam, contains(
            BOSTON_CELTICS,
            DENVER_NUGGETS,
            LOS_ANGELES_LAKERS,
            NEW_YORK_KNICKS,
            OKLAHOMA_CITY_THUNDERS,
            SAN_ANTONIO_SPURS
        ))

        verify(exactly = 2) {
            teamRepository.sortTeamsByName(true, NBA_TEAMS)
        }
    }

    @Test
    fun refreshTeamList_then_sortByTeamName_descending() = coroutinesRule.testDispatcher.runBlockingTest {
        coEvery { teamRepository.getNbaTeams() } returns NBA_TEAMS

        mainViewModel.refreshTeams()

        assertThat(resultCaptor.size, `is`(equalTo(2)))
        assertThat(resultCaptor[0], `is`(instanceOf(Result.Loading::class.java)))
        assertThat(resultCaptor[1], `is`(instanceOf(Result.Success::class.java)))

        val resultTeams = (resultCaptor[1] as Result.Success).data
        assertThat(resultTeams, `is`(equalTo(NBA_TEAMS)))

        coVerify(exactly = 1) {
            teamRepository.getNbaTeams()
        }

        mainViewModel.sortByTeamName(false)

        assertThat(resultCaptor.size, `is`(equalTo(3)))
        assertThat(resultCaptor[2], `is`(instanceOf(Result.Success::class.java)))

        val sortedTeam = (resultCaptor[2] as Result.Success).data
        assertThat(sortedTeam, contains(
            SAN_ANTONIO_SPURS,
            OKLAHOMA_CITY_THUNDERS,
            NEW_YORK_KNICKS,
            LOS_ANGELES_LAKERS,
            DENVER_NUGGETS,
            BOSTON_CELTICS
        ))

        verify(exactly = 1) {
            teamRepository.sortTeamsByName(true, NBA_TEAMS)
            teamRepository.sortTeamsByName(false, NBA_TEAMS)
        }
    }

    @Test
    fun refreshTeamList_then_sortByTeamWins_ascending() = coroutinesRule.testDispatcher.runBlockingTest {
        coEvery { teamRepository.getNbaTeams() } returns NBA_TEAMS

        mainViewModel.refreshTeams()

        assertThat(resultCaptor.size, `is`(equalTo(2)))
        assertThat(resultCaptor[0], `is`(instanceOf(Result.Loading::class.java)))
        assertThat(resultCaptor[1], `is`(instanceOf(Result.Success::class.java)))

        val resultTeams = (resultCaptor[1] as Result.Success).data
        assertThat(resultTeams, `is`(equalTo(NBA_TEAMS)))

        coVerify(exactly = 1) {
            teamRepository.getNbaTeams()
        }

        mainViewModel.sortByWins(true)

        assertThat(resultCaptor.size, `is`(equalTo(3)))
        assertThat(resultCaptor[2], `is`(instanceOf(Result.Success::class.java)))

        val sortedTeam = (resultCaptor[2] as Result.Success).data
        assertThat(sortedTeam, contains(
            NEW_YORK_KNICKS,
            SAN_ANTONIO_SPURS,
            BOSTON_CELTICS,
            OKLAHOMA_CITY_THUNDERS,
            DENVER_NUGGETS,
            LOS_ANGELES_LAKERS
        ))

        verify(exactly = 1) {
            teamRepository.sortTeamsByName(true, NBA_TEAMS)
            teamRepository.sortTeamsByWins(true, NBA_TEAMS)
        }
    }

    @Test
    fun refreshTeamList_then_sortByTeamWins_descending() = coroutinesRule.testDispatcher.runBlockingTest {
        coEvery { teamRepository.getNbaTeams() } returns NBA_TEAMS

        mainViewModel.refreshTeams()

        assertThat(resultCaptor.size, `is`(equalTo(2)))
        assertThat(resultCaptor[0], `is`(instanceOf(Result.Loading::class.java)))
        assertThat(resultCaptor[1], `is`(instanceOf(Result.Success::class.java)))

        val resultTeams = (resultCaptor[1] as Result.Success).data
        assertThat(resultTeams, `is`(equalTo(NBA_TEAMS)))

        coVerify(exactly = 1) {
            teamRepository.getNbaTeams()
        }

        mainViewModel.sortByWins(false)

        assertThat(resultCaptor.size, `is`(equalTo(3)))
        assertThat(resultCaptor[2], `is`(instanceOf(Result.Success::class.java)))

        val sortedTeam = (resultCaptor[2] as Result.Success).data
        assertThat(sortedTeam, contains(
            LOS_ANGELES_LAKERS,
            DENVER_NUGGETS,
            OKLAHOMA_CITY_THUNDERS,
            BOSTON_CELTICS,
            SAN_ANTONIO_SPURS,
            NEW_YORK_KNICKS
        ))

        verify(exactly = 1) {
            teamRepository.sortTeamsByName(true, NBA_TEAMS)
            teamRepository.sortTeamsByWins(false, NBA_TEAMS)
        }
    }

    @Test
    fun refreshTeamList_then_sortByTeamLosses_ascending() = coroutinesRule.testDispatcher.runBlockingTest {
        coEvery { teamRepository.getNbaTeams() } returns NBA_TEAMS

        mainViewModel.refreshTeams()

        assertThat(resultCaptor.size, `is`(equalTo(2)))
        assertThat(resultCaptor[0], `is`(instanceOf(Result.Loading::class.java)))
        assertThat(resultCaptor[1], `is`(instanceOf(Result.Success::class.java)))

        val resultTeams = (resultCaptor[1] as Result.Success).data
        assertThat(resultTeams, `is`(equalTo(NBA_TEAMS)))

        coVerify(exactly = 1) {
            teamRepository.getNbaTeams()
        }

        mainViewModel.sortByLosses(true)

        assertThat(resultCaptor.size, `is`(equalTo(3)))
        assertThat(resultCaptor[2], `is`(instanceOf(Result.Success::class.java)))

        val sortedTeam = (resultCaptor[2] as Result.Success).data
        assertThat(sortedTeam, contains(
            LOS_ANGELES_LAKERS,
            DENVER_NUGGETS,
            OKLAHOMA_CITY_THUNDERS,
            BOSTON_CELTICS,
            SAN_ANTONIO_SPURS,
            NEW_YORK_KNICKS
        ))

        verify(exactly = 1) {
            teamRepository.sortTeamsByName(true, NBA_TEAMS)
            teamRepository.sortTeamsByLosses(true, NBA_TEAMS)
        }
    }

    @Test
    fun refreshTeamList_then_sortByTeamLosses_descending() = coroutinesRule.testDispatcher.runBlockingTest {
        coEvery { teamRepository.getNbaTeams() } returns NBA_TEAMS

        mainViewModel.refreshTeams()

        assertThat(resultCaptor.size, `is`(equalTo(2)))
        assertThat(resultCaptor[0], `is`(instanceOf(Result.Loading::class.java)))
        assertThat(resultCaptor[1], `is`(instanceOf(Result.Success::class.java)))

        val resultTeams = (resultCaptor[1] as Result.Success).data
        assertThat(resultTeams, `is`(equalTo(NBA_TEAMS)))

        coVerify(exactly = 1) {
            teamRepository.getNbaTeams()
        }

        mainViewModel.sortByLosses(false)

        assertThat(resultCaptor.size, `is`(equalTo(3)))
        assertThat(resultCaptor[2], `is`(instanceOf(Result.Success::class.java)))

        val sortedTeam = (resultCaptor[2] as Result.Success).data
        assertThat(sortedTeam, contains(
            NEW_YORK_KNICKS,
            SAN_ANTONIO_SPURS,
            BOSTON_CELTICS,
            OKLAHOMA_CITY_THUNDERS,
            DENVER_NUGGETS,
            LOS_ANGELES_LAKERS
        ))

        verify(exactly = 1) {
            teamRepository.sortTeamsByName(true, NBA_TEAMS)
            teamRepository.sortTeamsByLosses(false, NBA_TEAMS)
        }
    }
}