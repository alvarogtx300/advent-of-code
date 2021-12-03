class Day3 {

    fun first(input: String): Int {
        val numbers = parseInput(input)
        val binarySize = numbers.first().size
        val halfSize = numbers.size / 2.0

        val gamma = numbers.reduce { acc, ints -> acc.zip(ints) { a, b -> a + b } }
            .map { if (it >= halfSize) 1 else 0 }
            .toBinaryNumber()
        val epsilon = gamma.xor("1".repeat(binarySize).toInt(2))

        return gamma * epsilon
    }

    fun second(input: String): Int {
        val numbers = parseInput(input)

        var oxygenNumbers = numbers
        var index = 0
        while (oxygenNumbers.size > 1) {
            val mostCommonBit = mostCommonBitAt(oxygenNumbers, index)
            oxygenNumbers = oxygenNumbers.filter { it[index] == mostCommonBit }
            index++
        }

        var co2Numbers = numbers
        index = 0
        while (co2Numbers.size > 1) {
            val mostCommonBit = mostCommonBitAt(co2Numbers, index).xor(1)
            co2Numbers = co2Numbers.filter { it[index] == mostCommonBit }
            index++
        }

        val oxygen = oxygenNumbers.first().toBinaryNumber()
        val co2 = co2Numbers.first().toBinaryNumber()

        return oxygen * co2
    }

    private fun mostCommonBitAt(input: List<List<Int>>, index: Int): Int {
        val halfSize = input.size / 2.0
        val count = input.map { it[index] }.reduce { acc, int -> acc + int }

        return if(count >= halfSize) 1 else 0
    }

    private fun parseInput(input: String): List<List<Int>> = input.lines()
        .map { it.chunked(size = 1).map(String::toInt) }

    private fun List<Int>.toBinaryNumber() = joinToString(separator = "").toInt(2)
}
