package capatan.kurt.nbateamviewer.repository

import capatan.kurt.nbateamviewer.core.*
import capatan.kurt.nbateamviewer.datasource.TeamService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.hamcrest.Matchers.*

class TeamRepositoryTest {

    private lateinit var teamService: TeamService
    private lateinit var teamRepository: TeamRepository

    @Before
    fun setUp() {
        teamService = mockk()
        teamRepository = TeamRepository(teamService)
    }

    @Test
    fun getNbaTeams_returns_nbaTeams() {
        coEvery { teamService.getTeams() } returns NBA_TEAMS

        val result = runBlocking { teamRepository.getNbaTeams() }

        coVerify { teamService.getTeams() }

        assertEquals(result, NBA_TEAMS)
    }

    @Test(expected = Exception::class)
    fun getNbaTeams_throws_exception() {
        val errorMessage = randomString()
        val fakeException = Exception(errorMessage)

        coEvery { teamService.getTeams() } throws fakeException

        runBlocking { teamRepository.getNbaTeams() }

        coVerify { teamService.getTeams() }
    }

    @Test
    fun sortTeamsByName_ascending() {
        val result = teamRepository.sortTeamsByName(true, NBA_TEAMS)

        assertThat (result, contains(
            BOSTON_CELTICS,
            DENVER_NUGGETS,
            LOS_ANGELES_LAKERS,
            NEW_YORK_KNICKS,
            OKLAHOMA_CITY_THUNDERS,
            SAN_ANTONIO_SPURS)
        )
    }

    @Test
    fun sortTeamsByName_descending() {
        val result = teamRepository.sortTeamsByName(false, NBA_TEAMS)

        assertThat (result, contains(
            SAN_ANTONIO_SPURS,
            OKLAHOMA_CITY_THUNDERS,
            NEW_YORK_KNICKS,
            LOS_ANGELES_LAKERS,
            DENVER_NUGGETS,
            BOSTON_CELTICS)
        )
    }

    @Test
    fun sortTeamsByWins_ascending() {
        val result = teamRepository.sortTeamsByWins(true, NBA_TEAMS)

        assertThat (result, contains(
            NEW_YORK_KNICKS,
            SAN_ANTONIO_SPURS,
            BOSTON_CELTICS,
            OKLAHOMA_CITY_THUNDERS,
            DENVER_NUGGETS,
            LOS_ANGELES_LAKERS)
        )
    }

    @Test
    fun sortTeamsByWins_descending() {
        val result = teamRepository.sortTeamsByWins(false, NBA_TEAMS)

        assertThat (result, contains(
            LOS_ANGELES_LAKERS,
            DENVER_NUGGETS,
            OKLAHOMA_CITY_THUNDERS,
            BOSTON_CELTICS,
            SAN_ANTONIO_SPURS,
            NEW_YORK_KNICKS)
        )
    }

    @Test
    fun sortTeamsByLosses_ascending() {
        val result = teamRepository.sortTeamsByLosses(true, NBA_TEAMS)

        assertThat (result, contains(
            LOS_ANGELES_LAKERS,
            DENVER_NUGGETS,
            OKLAHOMA_CITY_THUNDERS,
            BOSTON_CELTICS,
            SAN_ANTONIO_SPURS,
            NEW_YORK_KNICKS)
        )
    }

    @Test
    fun sortTeamsByLosses_descending() {
        val result = teamRepository.sortTeamsByLosses(false, NBA_TEAMS)

        assertThat (result, contains(
            NEW_YORK_KNICKS,
            SAN_ANTONIO_SPURS,
            BOSTON_CELTICS,
            OKLAHOMA_CITY_THUNDERS,
            DENVER_NUGGETS,
            LOS_ANGELES_LAKERS)
        )
    }
}