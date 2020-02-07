package capatan.kurt.nbateamviewer.ui.team_list

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import capatan.kurt.nbateamviewer.R
import capatan.kurt.nbateamviewer.core.BaseNbaTeamViewerApp
import capatan.kurt.nbateamviewer.databinding.FragmentTeamListBinding
import capatan.kurt.nbateamviewer.datasource.Team
import capatan.kurt.nbateamviewer.core.Result
import capatan.kurt.nbateamviewer.ui.MainViewModel
import com.google.android.material.snackbar.Snackbar

class TeamListFragment: Fragment() {

    private lateinit var teamListBinding: FragmentTeamListBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var teamListAdapter: TeamListAdapter
    private var errorSnackBar: Snackbar? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)

        if (!::viewModel.isInitialized) {
            val teamRepository = (activity!!.application as BaseNbaTeamViewerApp).teamRepository
            val viewModelFactory = MainViewModel.Factory(teamRepository)

            viewModel = ViewModelProvider(activity!!, viewModelFactory)[MainViewModel::class.java]
            viewModel.refreshTeams()
        }

        if (!::teamListAdapter.isInitialized) {
            teamListAdapter = TeamListAdapter(findNavController())
        }

        if (!::teamListBinding.isInitialized){
            teamListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_team_list, container, false)

            teamListBinding.recyclerViewTeamList.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = teamListAdapter
            }

            teamListBinding.swipeRefreshTeamList.setOnRefreshListener {
                viewModel.refreshTeams()
            }
        }

        viewModel.resultLiveData.observe(viewLifecycleOwner, Observer { result ->
            onResultReceived(result)
        })

        return teamListBinding.root
    }

    private fun onResultReceived(result: Result<List<Team>>) {
        when (result) {
            is Result.Loading -> {
                teamListBinding.swipeRefreshTeamList.isRefreshing = true
            }

            is Result.Failure -> {
                teamListBinding.swipeRefreshTeamList.isRefreshing = false

                val errorMessage = result.throwable.message ?: "An error occurred, try again."

                errorSnackBar = Snackbar.make (
                    teamListBinding.root,
                    errorMessage,
                    Snackbar.LENGTH_INDEFINITE
                )

                errorSnackBar!!.show()
            }

            is Result.Success -> {
                teamListBinding.swipeRefreshTeamList.isRefreshing = false
                if (errorSnackBar?.isShown == true) errorSnackBar?.dismiss()

                teamListAdapter.setTeamsData(result.data)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_team_list, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_sort_name_asc -> {
                viewModel.sortByTeamName()
                true
            }
            R.id.item_sort_name_desc -> {
                viewModel.sortByTeamName(false)
                true
            }
            R.id.item_sort_wins_asc -> {
                viewModel.sortByWins()
                true
            }
            R.id.item_sort_wins_desc -> {
                viewModel.sortByWins(false)
                true
            }
            R.id.item_sort_loss_asc -> {
                viewModel.sortByLosses()
                true
            }
            R.id.item_sort_loss_desc -> {
                viewModel.sortByLosses(false)
                true
            }
            else -> false
        }
    }

}