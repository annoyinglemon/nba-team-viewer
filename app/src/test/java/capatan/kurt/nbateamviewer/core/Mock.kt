package capatan.kurt.nbateamviewer.core

import capatan.kurt.nbateamviewer.datasource.Player
import capatan.kurt.nbateamviewer.datasource.Team
import java.util.*
import kotlin.random.Random

val BOSTON_CELTICS = Team(
    randomInt(), "Boston Celtics", 26, 24,
    generatePlayers()
)
val DENVER_NUGGETS = Team(
    randomInt(), "Denver Nuggets", 36, 14,
    generatePlayers()
)
val LOS_ANGELES_LAKERS = Team(
    randomInt(), "Los Angeles Lakers", 41, 8,
    generatePlayers()
)
val NEW_YORK_KNICKS = Team(
    randomInt(), "New York Knicks", 12, 38,
    generatePlayers()
)
val OKLAHOMA_CITY_THUNDERS = Team(
    randomInt(), "Oklahoma City Thunders", 30, 20,
    generatePlayers()
)
val SAN_ANTONIO_SPURS = Team(
    randomInt(), "San Antonio Spurs", 25, 25,
    generatePlayers()
)

val NBA_TEAMS = listOf(BOSTON_CELTICS, DENVER_NUGGETS, LOS_ANGELES_LAKERS, NEW_YORK_KNICKS, OKLAHOMA_CITY_THUNDERS, SAN_ANTONIO_SPURS)

private fun generatePlayers(): List<Player> {
    val player1 = Player(
        randomInt(),
        randomString(),
        randomString(),
        randomString(),
        randomInt()
    )
    val player2 = Player(
        randomInt(),
        randomString(),
        randomString(),
        randomString(),
        randomInt()
    )
    val player3 = Player(
        randomInt(),
        randomString(),
        randomString(),
        randomString(),
        randomInt()
    )
    val player4 = Player(
        randomInt(),
        randomString(),
        randomString(),
        randomString(),
        randomInt()
    )
    val player5 = Player(
        randomInt(),
        randomString(),
        randomString(),
        randomString(),
        randomInt()
    )

    return listOf(player1, player2, player3, player4, player5)
}

fun randomString(): String {
    return UUID.randomUUID().toString()
}

fun randomInt(): Int {
    return Random.nextInt()
}