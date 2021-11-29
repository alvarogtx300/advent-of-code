import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day1Test {

    private val day1 = Day1()

    @Test
    fun first() {
        val result = day1.first(
            """
            1721
            979
            366
            299
            675
            1456
        """.trimIndent()
        )

        assertEquals(514579, result)
    }

    @Test
    fun firstFile() {
        val result = day1.first(getResourceAsText("day1.input"))

        assertEquals(703131, result)
    }

    @Test
    fun second() {
        val result = day1.second(
            """
            1721
            979
            366
            299
            675
            1456
        """.trimIndent()
        )

        assertEquals(241861950, result)
    }

    @Test
    fun secondFile() {
        val result = day1.second(getResourceAsText("day1.input"))

        assertEquals(272423970, result)
    }
}