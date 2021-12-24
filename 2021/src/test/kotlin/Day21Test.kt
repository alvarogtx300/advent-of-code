import kotlin.test.Test
import kotlin.test.assertEquals

class Day21Test {
    private val day21 = Day21()

    @Test
    fun first() {
        val result = day21.first(TEST_INPUT)

        assertEquals(739785, result)
    }

    @Test
    fun firstFile() {
        val result = day21.first(getResourceAsText("day21.input"))

        assertEquals(998088, result)
    }

    @Test
    fun second() {
        val result = day21.second(TEST_INPUT)

        assertEquals(444356092776315, result)
    }

    @Test
    fun secondFile() {
        val result = day21.second(getResourceAsText("day21.input"))

        assertEquals(306621346123766, result)
    }

    companion object {
        val TEST_INPUT = """
            Player 1 starting position: 4
            Player 2 starting position: 8
        """.trimIndent()
    }
}