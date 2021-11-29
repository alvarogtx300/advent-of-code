import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day3Test {

    private val day3 = Day3()

    @Test
    fun first() {
        val result = day3.first(
            """
            ..##.......
            #...#...#..
            .#....#..#.
            ..#.#...#.#
            .#...##..#.
            ..#.##.....
            .#.#.#....#
            .#........#
            #.##...#...
            #...##....#
            .#..#...#.#
        """.trimIndent()
        )

        assertEquals(7, result)
    }

    @Test
    fun firstFile() {
        val result = day3.first(getResourceAsText("day3.input"))

        assertEquals(254, result)
    }

    @Test
    fun second() {
        val result = day3.second(
            """
            ..##.......
            #...#...#..
            .#....#..#.
            ..#.#...#.#
            .#...##..#.
            ..#.##.....
            .#.#.#....#
            .#........#
            #.##...#...
            #...##....#
            .#..#...#.#
        """.trimIndent()
        )

        assertEquals(336, result)
    }

    @Test
    fun secondFile() {
        val result = day3.second(getResourceAsText("day3.input"))

        assertEquals(1666768320, result)
    }
}