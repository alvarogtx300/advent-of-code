class Day1 {

    fun first(input: String): Int {
        val numbers = parseInput(input)

        return getIncrements(numbers)
    }

    fun second(input: String): Int {
        val numbers = parseInput(input)

        return getIncrements(numbers.windowed(size = 3) { it.sum() })
    }

    private fun getIncrements(numbers: List<Int>) =
        numbers.zipWithNext { first, second -> second > first }.count { it }

    private fun parseInput(input: String): List<Int> =
        input.split("\n", "\r\n").map(String::toInt)
}