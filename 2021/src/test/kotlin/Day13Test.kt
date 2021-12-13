import kotlin.test.Test
import kotlin.test.assertEquals

class Day13Test {
    private val day13 = Day13()

    @Test
    fun first() {
        val result = day13.first(TEST_INPUT)

        assertEquals(17, result)
    }

    @Test
    fun firstFile() {
        val result = day13.first(getResourceAsText("day13.input"))

        assertEquals(708, result)
    }

    @Test
    fun second() {
        val result = day13.second(TEST_INPUT)

        val expected = """
            #####
            #...#
            #...#
            #...#
            #####
            .....
            .....
            
        """.trimIndent()

        assertEquals(expected, result)
    }

    @Test
    fun secondFile() {
        val result = day13.second(getResourceAsText("day13.input"))

        val expected = """
            ####.###..#....#..#.###..###..####.#..#.
            #....#..#.#....#..#.#..#.#..#.#....#..#.
            ###..###..#....#..#.###..#..#.###..####.
            #....#..#.#....#..#.#..#.###..#....#..#.
            #....#..#.#....#..#.#..#.#.#..#....#..#.
            ####.###..####..##..###..#..#.#....#..#.
            
        """.trimIndent()

        assertEquals(expected, result)
    }

    companion object {
        val TEST_INPUT = """
            6,10
            0,14
            9,10
            0,3
            10,4
            4,11
            6,0
            6,12
            4,1
            0,13
            10,12
            3,4
            3,0
            8,4
            1,10
            2,14
            8,10
            9,0
            
            fold along y=7
            fold along x=5
        """.trimIndent()
    }
}