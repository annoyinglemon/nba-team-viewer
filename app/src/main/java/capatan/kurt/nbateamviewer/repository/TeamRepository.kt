package capatan.kurt.nbateamviewer.repository

import capatan.kurt.nbateamviewer.datasource.Team
import capatan.kurt.nbateamviewer.datasource.TeamService

class TeamRepository(private val teamService: TeamService) {

    suspend fun getNbaTeams(): List<Team> = teamService.getTeams()

    fun sortTeamsByName(ascending: Boolean,
                                teams: List<Team>): List<Team> {
        return if (ascending) {
            teams.sortedBy { it.name }
        } else {
            teams.sortedByDescending { it.name }
        }
    }

    fun sortTeamsByWins(ascending: Boolean,
                                teams: List<Team>): List<Team> {
        return if (ascending) {
            teams.sortedBy { it.wins }
        } else {
            teams.sortedByDescending { it.wins }
        }
    }

    fun sortTeamsByLosses(ascending: Boolean,
                                  teams: List<Team>): List<Team> {
        return if (ascending) {
            teams.sortedBy { it.losses }
        } else {
            teams.sortedByDescending { it.losses }
        }
    }
}