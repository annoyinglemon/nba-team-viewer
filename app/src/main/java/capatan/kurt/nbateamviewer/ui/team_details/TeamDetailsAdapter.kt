package capatan.kurt.nbateamviewer.ui.team_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import capatan.kurt.nbateamviewer.R
import capatan.kurt.nbateamviewer.databinding.ItemPlayerBinding
import capatan.kurt.nbateamviewer.databinding.ItemTeamHeaderBinding
import capatan.kurt.nbateamviewer.datasource.Team

class TeamDetailsAdapter(val team: Team): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int = team.players.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            ItemType.TYPE_TEAM.ordinal
        } else {
            ItemType.TYPE_PLAYER.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (ItemType.values()[viewType]) {
            ItemType.TYPE_TEAM -> {
                val headerBinding: ItemTeamHeaderBinding = DataBindingUtil.inflate(inflater, R.layout.item_team_header, parent, false)
                TeamHeaderViewHolder(headerBinding)
            }
            else -> {
                val playerBinding: ItemPlayerBinding = DataBindingUtil.inflate(inflater, R.layout.item_player, parent, false)
                PlayerViewHolder(playerBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TeamHeaderViewHolder) {
            holder.headerBinding.wins = team.wins.toString()
            holder.headerBinding.losses = team.losses.toString()

        } else if (holder is PlayerViewHolder) {
            holder.playerBinding.player = team.players[position - 1]
        }
    }

    private data class TeamHeaderViewHolder(val headerBinding: ItemTeamHeaderBinding): RecyclerView.ViewHolder(headerBinding.root)
    private data class PlayerViewHolder(val playerBinding: ItemPlayerBinding): RecyclerView.ViewHolder(playerBinding.root)

    private enum class ItemType {
        TYPE_TEAM,
        TYPE_PLAYER
    }
}