class Day8 {

    fun first(input: String): Int {
        val displays = parseInput(input)

        return displays.sumOf(Display::numOfSimpleSegments)
    }

    fun second(input: String): Int {
        val displays = parseInput(input)

        return displays.sumOf(Display::getOutputNumber)
    }

    private fun parseInput(input: String): List<Display> = input.lines()
        .map { line -> line.split(" | ").map { it.split(" ") } }
        .map { (input, output) -> Display(input.take(10), output.take(4)) }
}

class Display(rawInput: List<String>, rawOutput: List<String>) {
    private val input: List<Digit> = rawInput.map(::Digit)
    private val output: List<Digit> = rawOutput.map(::Digit)
    private val inputBySize: Map<Int, List<Digit>> = input.groupBy(Digit::length)

    fun numOfSimpleSegments(): Int = output.fold(0) { acc, s ->
        when (s.length) {
            2, 3, 4, 7 -> acc + 1
            else -> acc
        }
    }

    fun getOutputNumber(): Int {
        val decodedDigits = decodeDigits()
        return output.map { decodedDigits.getValue(it) }.joinToString(separator = "").toInt()
    }

    private fun decodeDigits(): Map<Digit, Int> {
        val digits = Array(size = 10) { Digit.UNKNOWN }

        digits[1] = inputBySize.getValue(2).single()
        digits[4] = inputBySize.getValue(4).single()
        digits[7] = inputBySize.getValue(3).single()
        digits[8] = inputBySize.getValue(7).single()

        val sixSegments = inputBySize.getValue(6)
        digits[0] = sixSegments.filterNot { it.containsAllSegments((digits[4] - digits[1])) }.single()
        digits[9] = (sixSegments - digits[0]).single { it.containsAllSegments(digits[1]) }
        digits[6] = (sixSegments - digits[0] - digits[9]).single()

        val fiveSegments = inputBySize.getValue(5)
        digits[3] = fiveSegments.single { it.containsAllSegments(digits[1]) }
        digits[2] = fiveSegments.single { it.containsAllSegments(digits[0] - digits[9]) }
        digits[5] = (fiveSegments - digits[3] - digits[2]).single()

        return digits.indices.associateBy { digits[it] }
    }
}

class Digit(value: String) {
    val length = value.length
    val segments = value.toCharArray().toSet()

    fun containsAllSegments(other: Digit) = segments.containsAll(other.segments)

    operator fun minus(other: Digit) = (segments - other.segments).toDigit()

    override fun equals(other: Any?) = other is Digit && segments == other.segments

    override fun hashCode() = segments.hashCode()

    private fun Set<Char>.toDigit() = Digit(joinToString(separator = ""))

    companion object {
        val UNKNOWN = Digit("")
    }
}
