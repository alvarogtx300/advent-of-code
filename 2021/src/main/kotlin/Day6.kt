class Day6 {

    fun first(input: String): Int {
        val numbers = parseInput(input)
        val sequence = generateSequence(numbers) {
            it.flatMap { n ->
                when (n) {
                    0 -> listOf(6, 8)
                    else -> listOf(n.dec())
                }
            }
        }

        return sequence.elementAt(80).size
    }

    fun second(input: String): Long {
        val numbers = parseInput(input)
        val numFish = LongArray(9)
        numbers.forEach { numFish[it]++ }

        for (day in 1..256) {
            val zeros = numFish[0]
            for (i in 0..7) {
                numFish[i] = numFish[i + 1]
            }
            numFish[8] = zeros
            numFish[6] += zeros
        }

        return numFish.sum()
    }

    private fun parseInput(input: String): List<Int> = input.split(",").map(String::toInt)
}
