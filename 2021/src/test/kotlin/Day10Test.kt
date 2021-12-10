import kotlin.test.Test
import kotlin.test.assertEquals

class Day10Test {
    private val day10 = Day10()

    @Test
    fun first() {
        val result = day10.first(TEST_INPUT)

        assertEquals(26397, result)
    }

    @Test
    fun firstFile() {
        val result = day10.first(getResourceAsText("day10.input"))

        assertEquals(339537, result)
    }

    @Test
    fun second() {
        val result = day10.second(TEST_INPUT)

        assertEquals(288957, result)
    }

    @Test
    fun secondFile() {
        val result = day10.second(getResourceAsText("day10.input"))

        assertEquals(2412013412, result)
    }

    companion object {
        val TEST_INPUT = """
            [({(<(())[]>[[{[]{<()<>>
            [(()[<>])]({[<{<<[]>>(
            {([(<{}[<>[]}>{[]{[(<()>
            (((({<>}<{<{<>}{[]{[]{}
            [[<[([]))<([[{}[[()]]]
            [{[{({}]{}}([{[{{{}}([]
            {<[[]]>}<{[{[{[]{()[[[]
            [<(<(<(<{}))><([]([]()
            <{([([[(<>()){}]>(<<{{
            <{([{{}}[<[[[<>{}]]]>[]]
        """.trimIndent()
    }
}