import kotlin.test.Test
import kotlin.test.assertEquals

class Day7Test {
    private val day7 = Day7()

    @Test
    fun first() {
        val result = day7.first(TEST_INPUT)

        assertEquals(37, result)
    }

    @Test
    fun firstFile() {
        val result = day7.first(getResourceAsText("day7.input"))

        assertEquals(328318, result)
    }

    @Test
    fun second() {
        val result = day7.second(TEST_INPUT)

        assertEquals(168, result)
    }

    @Test
    fun secondFile() {
        val result = day7.second(getResourceAsText("day7.input"))

        assertEquals(89791146, result)
    }

    companion object {
        const val TEST_INPUT = "16,1,2,0,4,2,7,1,2,14"
    }
}