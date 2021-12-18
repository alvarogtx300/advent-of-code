import kotlin.test.Test
import kotlin.test.assertEquals

class Day17Test {
    private val day17 = Day17()

    @Test
    fun first() {
        val result = day17.first(TEST_INPUT)

        assertEquals(45, result)
    }

    @Test
    fun firstFile() {
        val result = day17.first(getResourceAsText("day17.input"))

        assertEquals(2850, result)
    }

    @Test
    fun second() {
        val result = day17.second(TEST_INPUT)

        assertEquals(112, result)
    }

    @Test
    fun secondFile() {
        val result = day17.second(getResourceAsText("day17.input"))

        assertEquals(1117, result)
    }

    companion object {
        const val TEST_INPUT = "target area: x=20..30, y=-10..-5"
    }
}