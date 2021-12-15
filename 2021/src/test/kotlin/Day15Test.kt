import kotlin.test.Test
import kotlin.test.assertEquals

class Day15Test {
    private val day15 = Day15()

    @Test
    fun first() {
        val result = day15.first(TEST_INPUT)

        assertEquals(40, result)
    }

    @Test
    fun firstFile() {
        val result = day15.first(getResourceAsText("day15.input"))

        assertEquals(366, result)
    }

    @Test
    fun second() {
        val result = day15.second(TEST_INPUT)

        assertEquals(315, result)
    }

    @Test
    fun secondFile() {
        val result = day15.second(getResourceAsText("day15.input"))

        assertEquals(2829, result)
    }

    companion object {
        val TEST_INPUT = """
            1163751742
            1381373672
            2136511328
            3694931569
            7463417111
            1319128137
            1359912421
            3125421639
            1293138521
            2311944581
        """.trimIndent()
    }
}