class Day1 {

    fun first(input: String): Int {
        val numbers = parseInput(input)

        for (i in numbers) {
            for (j in numbers) {
                if (i + j == 2020) return i * j
            }
        }

        error("Not found")
    }

    fun second(input: String): Int {
        val numbers = parseInput(input)

        for (i in numbers) {
            for (j in numbers) {
                for (k in numbers)
                    if (i + j + k == 2020) return i * j * k
            }
        }

        error("Not found")
    }

    private fun parseInput(input: String): List<Int> =
        input.split("\n", "\r\n").map(String::toInt)
}