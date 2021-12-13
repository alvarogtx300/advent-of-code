import kotlin.test.Test
import kotlin.test.assertEquals

class Day12Test {
    private val day12 = Day12()

    @Test
    fun first() {
        val result = day12.first(TEST_INPUT)

        assertEquals(10, result)
    }

    @Test
    fun firstFile() {
        val result = day12.first(getResourceAsText("day12.input"))

        assertEquals(4885, result)
    }

    @Test
    fun second() {
        val result = day12.second(TEST_INPUT)

        assertEquals(36, result)
    }

    @Test
    fun secondFile() {
        val result = day12.second(getResourceAsText("day12.input"))

        assertEquals(117095, result)
    }

    companion object {
        val TEST_INPUT = """
            start-A
            start-b
            A-c
            A-b
            b-d
            A-end
            b-end
        """.trimIndent()
    }
}