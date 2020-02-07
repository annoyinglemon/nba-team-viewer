package capatan.kurt.nbateamviewer.ui.team_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import capatan.kurt.nbateamviewer.R
import capatan.kurt.nbateamviewer.databinding.FragmentTeamDetailsBinding

class TeamDetailsFragment: Fragment() {

    private val args: TeamDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val teamDetailsBinding: FragmentTeamDetailsBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_team_details, container, false)

        val teamDetailsAdapter = TeamDetailsAdapter(args.team)

        teamDetailsBinding.recyclerViewTeamDetails.apply {
            layoutManager = LinearLayoutManager(activity!!)
            adapter = teamDetailsAdapter
        }

        return teamDetailsBinding.root
    }

}