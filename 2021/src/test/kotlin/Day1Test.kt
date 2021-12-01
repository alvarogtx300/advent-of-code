import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Test {
    private val day1 = Day1()

    @Test
    fun first() {
        val result = day1.first(
            """
            199
            200
            208
            210
            200
            207
            240
            269
            260
            263
        """.trimIndent()
        )

        assertEquals(7, result)
    }

    @Test
    fun firstFile() {
        val result = day1.first(getResourceAsText("day1.input"))

        assertEquals(1696, result)
    }

    @Test
    fun second() {
        val result = day1.second(
            """
            199
            200
            208
            210
            200
            207
            240
            269
            260
            263
        """.trimIndent()
        )

        assertEquals(5, result)
    }

    @Test
    fun secondFile() {
        val result = day1.second(getResourceAsText("day1.input"))

        assertEquals(1737, result)
    }
}