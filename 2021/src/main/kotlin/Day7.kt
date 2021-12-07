import kotlin.math.abs
import kotlin.math.roundToInt

class Day7 {

    fun first(input: String): Int {
        val numbers = parseInput(input)
        val moves = numbers.median()

        return numbers.sumOf { abs(it - moves) }
    }

    fun second(input: String): Int {
        val numbers = parseInput(input)
        val moves = numbers.means()

        return moves.minOf { mean -> numbers.sumOf { fuelCost(abs(mean - it)) } }
    }

    private fun List<Int>.median() = sorted().let { (it[size / 2] + it[(size - 1) / 2]) / 2 }
    private fun List<Int>.means() = (sum() / size.toDouble()).let { listOf(it.toInt(), it.roundToInt()) }
    private fun fuelCost(n: Int) = (n * (n + 1)) / 2

    private fun parseInput(input: String): List<Int> = input.split(",").map(String::toInt)
}
