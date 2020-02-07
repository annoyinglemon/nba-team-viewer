package capatan.kurt.nbateamviewer.ui.team_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import capatan.kurt.nbateamviewer.R
import capatan.kurt.nbateamviewer.databinding.ItemTeamBinding
import capatan.kurt.nbateamviewer.datasource.Team

class TeamListAdapter(private val mainNavController: NavController): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var teams: List<Team> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val teamBinding: ItemTeamBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_team, parent, false)
        return TeamViewHolder(teamBinding)
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TeamViewHolder) {
            val team = teams[position]
            holder.teamBinding.team = team

            holder.itemView.setOnClickListener {
                val teamDetailsAction = TeamListFragmentDirections
                    .actionTeamListFragmentToTeamDetailsFragment(team, team.name)
                mainNavController.navigate(teamDetailsAction)
            }
        }
    }

    fun setTeamsData(newTeamsList: List<Team>) {
        val diffCallback = TeamListDiffCallback(teams, newTeamsList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        teams = newTeamsList
        diffResult.dispatchUpdatesTo(this)
    }

    private data class TeamViewHolder(val teamBinding: ItemTeamBinding): RecyclerView.ViewHolder(teamBinding.root)

    private class TeamListDiffCallback(private val oldList: List<Team>, private val newList: List<Team>): DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].name == newList[newItemPosition].name
                    && oldList[oldItemPosition].wins == newList[newItemPosition].wins
                    && oldList[oldItemPosition].losses == newList[newItemPosition].losses
        }

    }

}