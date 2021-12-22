import kotlin.test.Test
import kotlin.test.assertEquals

class Day20Test {
    private val day20 = Day20()

    @Test
    fun first() {
        val result = day20.first(TEST_INPUT)

        assertEquals(35, result)
    }

    @Test
    fun firstFile() {
        val result = day20.first(getResourceAsText("day20.input"))

        assertEquals(5464, result)
    }

    @Test
    fun second() {
        val result = day20.second(TEST_INPUT)

        assertEquals(3351, result)
    }

    @Test
    fun secondFile() {
        val result = day20.second(getResourceAsText("day20.input"))

        assertEquals(19228, result)
    }

    companion object {
        val TEST_INPUT = """
            ..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#
            
            #..#.
            #....
            ##..#
            ..#..
            ..###
        """.trimIndent()
    }
}