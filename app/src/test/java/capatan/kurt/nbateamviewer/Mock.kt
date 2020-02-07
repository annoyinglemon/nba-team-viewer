package capatan.kurt.nbateamviewer

import capatan.kurt.nbateamviewer.datasource.Player
import capatan.kurt.nbateamviewer.datasource.Team
import java.util.*
import kotlin.random.Random

fun generatePlayers(): List<Player> {
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