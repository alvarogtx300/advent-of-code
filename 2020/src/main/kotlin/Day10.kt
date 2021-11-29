class Day10 {

    fun first(input: String): Int {
        val joltList = getJoltList(input)

        return joltList.calculateNumDifferences(1) * joltList.calculateNumDifferences(3)
    }

    fun second(input: String): Long {
        val joltList = getJoltList(input)
        val differenceList = joltList.calculateDifferenceList()

        var ones = 0
        var combinations = 1L

        for (x in differenceList) {
            when (x) {
                1 -> ones++
                3 -> {
                    if (ones != 0) combinations *= tribonacci(ones)
                    ones = 0
                }
            }
        }

        return combinations
    }

    private fun getJoltList(input: String): MutableList<Int> {
        val joltList = parseInput(input)
        joltList.add(0)
        joltList.sort()

        val deviceJolt = joltList.last() + 3
        joltList.add(deviceJolt)
        return joltList
    }

    private fun List<Int>.calculateDifferenceList(): List<Int> =
        zipWithNext { a, b -> b - a }

    private fun List<Int>.calculateNumDifferences(difference: Int): Int =
        calculateDifferenceList()
            .filter { it == difference }
            .count()

    private fun tribonacci(num: Int): Int =
        tribonacciSequence().elementAt(num)

    private fun tribonacciSequence(): Sequence<Int> =
        generateSequence(Triple(0, 0, 1)) { Triple(it.second, it.third, it.first + it.second + it.third) }
            .map { it.third }

    private fun parseInput(input: String): MutableList<Int> =
        input.split("\n", "\r\n").map(String::toInt).toMutableList()
}