import kotlin.test.Test
import kotlin.test.assertEquals

class Day2Test {
    private val day2 = Day2()

    @Test
    fun first() {
        val result = day2.first(TEST_INPUT)

        assertEquals(150, result)
    }

    @Test
    fun firstFile() {
        val result = day2.first(getResourceAsText("day2.input"))

        assertEquals(1692075, result)
    }

    @Test
    fun second() {
        val result = day2.second(TEST_INPUT)

        assertEquals(900, result)
    }

    @Test
    fun secondFile() {
        val result = day2.second(getResourceAsText("day2.input"))

        assertEquals(1749524700, result)
    }

    companion object {
        val TEST_INPUT = """
            forward 5
            down 5
            forward 8
            up 3
            down 8
            forward 2
        """.trimIndent()
    }
}