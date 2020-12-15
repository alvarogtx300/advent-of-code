import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day2Test {

    private val day2 = Day2()

    @Test
    fun first() {
        val result = day2.first(
            """
            1-3 a: abcde
            1-3 b: cdefg
            2-9 c: ccccccccc
        """.trimIndent()
        )

        assertEquals(2, result)
    }

    @Test
    fun firstFile() {
        val result = day2.first(getResourceAsText("day2.input"))

        assertEquals(548, result)
    }

    @Test
    fun second() {
        val result = day2.second(
            """
            1-3 a: abcde
            1-3 b: cdefg
            2-9 c: ccccccccc
        """.trimIndent()
        )

        assertEquals(1, result)
    }

    @Test
    fun secondFile() {
        val result = day2.second(getResourceAsText("day2.input"))

        assertEquals(502, result)
    }
}