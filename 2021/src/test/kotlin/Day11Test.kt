import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test {
    private val day11 = Day11()

    @Test
    fun first() {
        val result = day11.first(TEST_INPUT)

        assertEquals(1656, result)
    }

    @Test
    fun firstFile() {
        val result = day11.first(getResourceAsText("day11.input"))

        assertEquals(1599, result)
    }

    @Test
    fun second() {
        val result = day11.second(TEST_INPUT)

        assertEquals(195, result)
    }

    @Test
    fun secondFile() {
        val result = day11.second(getResourceAsText("day11.input"))

        assertEquals(418, result)
    }

    companion object {
        val TEST_INPUT = """
            5483143223
            2745854711
            5264556173
            6141336146
            6357385478
            4167524645
            2176841721
            6882881134
            4846848554
            5283751526
        """.trimIndent()
    }
}