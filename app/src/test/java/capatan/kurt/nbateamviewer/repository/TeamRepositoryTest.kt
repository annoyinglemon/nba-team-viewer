package capatan.kurt.nbateamviewer.repository

import capatan.kurt.nbateamviewer.datasource.Team
import capatan.kurt.nbateamviewer.datasource.TeamService
import capatan.kurt.nbateamviewer.generatePlayers
import capatan.kurt.nbateamviewer.randomInt
import capatan.kurt.nbateamviewer.randomString
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.hamcrest.Matchers.*

class TeamRepositoryTest {

    private val bostonCeltics = Team(
        randomInt(), "Boston Celtics", 26, 24,
        generatePlayers()
    )
    private val denverNuggets = Team(
        randomInt(), "Denver Nuggets", 36, 14,
        generatePlayers()
    )
    private val losAngelesLakers = Team(
        randomInt(), "Los Angeles Lakers", 41, 8,
        generatePlayers()
    )
    private val newYorkKnicks = Team(
        randomInt(), "New York Knicks", 12, 38,
        generatePlayers()
    )
    private val oklahomaCityThunders = Team(
        randomInt(), "Oklahoma City Thunders", 30, 20,
        generatePlayers()
    )
    private val sanAntonioSpurs = Team(
        randomInt(), "San Antonio Spurs", 25, 25,
        generatePlayers()
    )

    private val nbaTeams = listOf(bostonCeltics, denverNuggets, losAngelesLakers,
        newYorkKnicks, oklahomaCityThunders, sanAntonioSpurs)

    private lateinit var teamService: TeamService
    private lateinit var teamRepository: TeamRepository

    @Before
    fun setUp() {
        teamService = mockk()
        teamRepository = TeamRepository(teamService)
    }

    @Test
    fun getNbaTeams_returns_nbaTeams() {
        coEvery { teamService.getTeams() } returns nbaTeams

        val result = runBlocking { teamRepository.getNbaTeams() }

        coVerify { teamService.getTeams() }

        assertEquals(result, nbaTeams)
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
        val result = runBlocking { teamRepository.sortTeamsByName(true, nbaTeams) }

        assertThat (result, contains(
            bostonCeltics,
            denverNuggets,
            losAngelesLakers,
            newYorkKnicks,
            oklahomaCityThunders,
            sanAntonioSpurs)
        )
    }

    @Test
    fun sortTeamsByName_descending() {
        val result = runBlocking { teamRepository.sortTeamsByName(false, nbaTeams) }

        assertThat (result, contains(
            sanAntonioSpurs,
            oklahomaCityThunders,
            newYorkKnicks,
            losAngelesLakers,
            denverNuggets,
            bostonCeltics)
        )
    }

    @Test
    fun sortTeamsByWins_ascending() {
        val result = runBlocking { teamRepository.sortTeamsByWins(true, nbaTeams) }

        assertThat (result, contains(
            newYorkKnicks,
            sanAntonioSpurs,
            bostonCeltics,
            oklahomaCityThunders,
            denverNuggets,
            losAngelesLakers)
        )
    }

    @Test
    fun sortTeamsByWins_descending() {
        val result = runBlocking { teamRepository.sortTeamsByWins(false, nbaTeams) }

        assertThat (result, contains(
            losAngelesLakers,
            denverNuggets,
            oklahomaCityThunders,
            bostonCeltics,
            sanAntonioSpurs,
            newYorkKnicks)
        )
    }

    @Test
    fun sortTeamsByLosses_ascending() {
        val result = runBlocking { teamRepository.sortTeamsByLosses(true, nbaTeams) }

        assertThat (result, contains(
            losAngelesLakers,
            denverNuggets,
            oklahomaCityThunders,
            bostonCeltics,
            sanAntonioSpurs,
            newYorkKnicks)
        )
    }

    @Test
    fun sortTeamsByLosses_descending() {
        val result = runBlocking { teamRepository.sortTeamsByLosses(false, nbaTeams) }

        assertThat (result, contains(
            newYorkKnicks,
            sanAntonioSpurs,
            bostonCeltics,
            oklahomaCityThunders,
            denverNuggets,
            losAngelesLakers)
        )
    }
}