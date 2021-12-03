import kotlin.test.Test
import kotlin.test.assertEquals

class Day3Test {
    private val day3 = Day3()

    @Test
    fun first() {
        val result = day3.first(TEST_INPUT)

        assertEquals(198, result)
    }

    @Test
    fun firstFile() {
        val result = day3.first(getResourceAsText("day3.input"))

        assertEquals(4006064, result)
    }

    @Test
    fun second() {
        val result = day3.second(TEST_INPUT)

        assertEquals(230, result)
    }

    @Test
    fun secondFile() {
        val result = day3.second(getResourceAsText("day3.input"))

        assertEquals(5941884, result)
    }

    companion object {
        val TEST_INPUT = """
            00100
            11110
            10110
            10111
            10101
            01111
            00111
            11100
            10000
            11001
            00010
            01010
        """.trimIndent()
    }
}