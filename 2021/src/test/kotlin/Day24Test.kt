import kotlin.test.Test
import kotlin.test.assertEquals

class Day24Test {
    private val day24 = Day24()

    @Test
    fun firstFile() {
        val result = day24.first(getResourceAsText("day24.input"))

        assertEquals(29991993698469, result)
    }

    @Test
    fun secondFile() {
        val result = day24.second(getResourceAsText("day24.input"))

        assertEquals(14691271141118, result)
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