package capatan.kurt.nbateamviewer.repository

import capatan.kurt.nbateamviewer.datasource.Team
import capatan.kurt.nbateamviewer.datasource.TeamService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TeamRepository(private val teamService: TeamService) {

    suspend fun getNbaTeams(): List<Team> = withContext(Dispatchers.IO) {
        teamService.getTeams()
    }

    suspend fun sortTeamsByName(ascending: Boolean,
                                teams: List<Team>): List<Team> = withContext(Dispatchers.Default) {
        if (ascending) {
            teams.sortedBy { it.name }
        } else {
            teams.sortedByDescending { it.name }
        }
    }

    suspend fun sortTeamsByWins(ascending: Boolean,
                                teams: List<Team>): List<Team> = withContext(Dispatchers.Default) {
        if (ascending) {
            teams.sortedBy { it.wins }
        } else {
            teams.sortedByDescending { it.wins }
        }
    }

    suspend fun sortTeamsByLosses(ascending: Boolean,
                                  teams: List<Team>): List<Team> = withContext(Dispatchers.Default) {
        if (ascending) {
            teams.sortedBy { it.losses }
        } else {
            teams.sortedByDescending { it.losses }
        }
    }
}