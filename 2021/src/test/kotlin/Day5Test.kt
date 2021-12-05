import kotlin.test.Test
import kotlin.test.assertEquals

class Day5Test {
    private val day5 = Day5()

    @Test
    fun first() {
        val result = day5.first(TEST_INPUT)

        assertEquals(5, result)
    }

    @Test
    fun firstFile() {
        val result = day5.first(getResourceAsText("day5.input"))

        assertEquals(7318, result)
    }

    @Test
    fun second() {
        val result = day5.second(TEST_INPUT)

        assertEquals(12, result)
    }

    @Test
    fun secondFile() {
        val result = day5.second(getResourceAsText("day5.input"))

        assertEquals(19939, result)
    }

    companion object {
        val TEST_INPUT = """
            0,9 -> 5,9
            8,0 -> 0,8
            9,4 -> 3,4
            2,2 -> 2,1
            7,0 -> 7,4
            6,4 -> 2,0
            0,9 -> 2,9
            3,4 -> 1,4
            0,0 -> 8,8
            5,5 -> 8,2
        """.trimIndent()
    }
}