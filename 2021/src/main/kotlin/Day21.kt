import kotlin.math.max

class Day21 {

    fun first(input: String): Int {
        val players = parseInput(input, winPoints = 1000)
        val dice = Dice()
        var rolls = 0

        while (players.none(Player::isWinner)) {
            val moves = dice.next(rolls = 3).sum()
            players[rolls % 2].move(moves)
            rolls += 3
        }

        return players.first { !it.isWinner() }.score * rolls
    }

    fun second(input: String): Long {
        val players = parseInput(input, winPoints = 21)
        val diracDice = DiracDice()

        var player1Wins = 0L
        var player2Wins = 0L

        fun diracDice(roll: Int, player1: Player, player2: Player, lastRoll: List<Int>) {
            if (player1.isWinner()) {
                player1Wins += lastRoll.map { diracDice.distribution.getValue(it).toLong() }.reduce(Long::times)
                return
            }
            if (player2.isWinner()) {
                player2Wins += lastRoll.map { diracDice.distribution.getValue(it).toLong() }.reduce(Long::times)
                return
            }

            diracDice.distribution.keys.forEach { moves ->
                if (roll % 2 == 0) {
                    diracDice(roll + 1, player1.moveCopy(moves), player2.copy(), lastRoll + moves)
                } else {
                    diracDice(roll + 1, player1.copy(), player2.moveCopy(moves), lastRoll + moves)
                }
            }
        }

        diracDice(0, players[0], players[1], emptyList())

        return max(player1Wins, player2Wins)
    }

    private fun parseInput(input: String, winPoints: Int): List<Player> = input.lines()
        .map { Player(it.last().digitToInt() - 1, winPoints) }

    class Dice {
        private val sequence = generateSequence(1) { it % 100 + 1 }.iterator()

        fun next(rolls: Int) = buildList {
            repeat(times = rolls) { add(sequence.next()) }
        }
    }

    class DiracDice {
        private val options = buildList {
            for (i in 1..3) {
                for (j in 1..3) {
                    for (k in 1..3) {
                        add(i + j + k)
                    }
                }
            }
        }

        val distribution = options.groupingBy { it }.eachCount()
    }

    data class Player(private var position: Int, val winPoints: Int, var score: Int = 0) {

        fun move(moves: Int) {
            position = (position + moves) % 10
            score += position + 1
        }

        fun moveCopy(moves: Int): Player {
            val nextPosition = (position + moves) % 10
            val nextScore = score + nextPosition + 1
            return copy(position = nextPosition, score = nextScore)
        }

        fun isWinner() = score >= winPoints
    }
}
