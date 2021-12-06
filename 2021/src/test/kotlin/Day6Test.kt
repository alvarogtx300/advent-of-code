import kotlin.test.Test
import kotlin.test.assertEquals

class Day6Test {
    private val day6 = Day6()

    @Test
    fun first() {
        val result = day6.first(TEST_INPUT)

        assertEquals(5934, result)
    }

    @Test
    fun firstFile() {
        val result = day6.first(getResourceAsText("day6.input"))

        assertEquals(395627, result)
    }

    @Test
    fun second() {
        val result = day6.second(TEST_INPUT)

        assertEquals(26984457539, result)
    }

    @Test
    fun secondFile() {
        val result = day6.second(getResourceAsText("day6.input"))

        assertEquals(1767323539209, result)
    }

    companion object {
        const val TEST_INPUT = "3,4,3,1,2"
    }
}