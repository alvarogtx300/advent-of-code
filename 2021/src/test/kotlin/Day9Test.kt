import kotlin.test.Test
import kotlin.test.assertEquals

class Day9Test {
    private val day9 = Day9()

    @Test
    fun first() {
        val result = day9.first(TEST_INPUT)

        assertEquals(15, result)
    }

    @Test
    fun firstFile() {
        val result = day9.first(getResourceAsText("day9.input"))

        assertEquals(594, result)
    }

    @Test
    fun second() {
        val result = day9.second(TEST_INPUT)

        assertEquals(1134, result)
    }

    @Test
    fun secondFile() {
        val result = day9.second(getResourceAsText("day9.input"))

        assertEquals(858494, result)
    }

    companion object {
        val TEST_INPUT = """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
        """.trimIndent()
    }
}