import kotlin.test.Test
import kotlin.test.assertEquals

class Day23Test {
    private val day23 = Day23()

    @Test
    fun first() {
        val result = day23.first(TEST_INPUT)

        assertEquals(12521, result)
    }

    @Test
    fun firstFile() {
        val result = day23.first(getResourceAsText("day23.input"))

        assertEquals(15299, result)
    }

    @Test
    fun second() {
        val result = day23.second(TEST_INPUT)

        assertEquals(44169, result)
    }

    @Test
    fun secondFile() {
        val result = day23.second(getResourceAsText("day23.input"))

        assertEquals(47193, result)
    }

    companion object {
        val TEST_INPUT = """
            #############
            #...........#
            ###B#C#B#D###
              #A#D#C#A#
              #########
        """.trimIndent()
    }
}