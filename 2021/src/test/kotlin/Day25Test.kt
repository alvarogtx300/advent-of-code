import kotlin.test.Test
import kotlin.test.assertEquals

class Day25Test {
    private val day25 = Day25()

    @Test
    fun first() {
        val result = day25.first(TEST_INPUT)

        assertEquals(58, result)
    }

    @Test
    fun firstFile() {
        val result = day25.first(getResourceAsText("day25.input"))

        assertEquals(305, result)
    }

    companion object {
        val TEST_INPUT = """
            v...>>.vv>
            .vv>>.vv..
            >>.>v>...v
            >>v>>.>.v.
            v>v.vv.v..
            >.>>..v...
            .vv..>.>v.
            v.v..>>v.v
            ....v..v.>
        """.trimIndent()
    }
}