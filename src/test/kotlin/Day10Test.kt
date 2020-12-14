import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day10Test {

    private val day10 = Day10()

    @Test
    fun first() {
        val joltDifference = day10.first(
            """
            16
            10
            15
            5
            1
            11
            7
            19
            6
            12
            4
        """.trimIndent()
        )

        assertEquals(35, joltDifference)
    }

    @Test
    fun firstBig() {
        val joltDifference = day10.first(
            """
            28
            33
            18
            42
            31
            14
            46
            20
            48
            47
            24
            23
            49
            45
            19
            38
            39
            11
            1
            32
            25
            35
            8
            17
            7
            9
            4
            2
            34
            10
            3
        """.trimIndent()
        )

        assertEquals(220, joltDifference)
    }

    @Test
    fun firstFile() {
        val joltDifference = day10.first(getResourceAsText("day10.input"))

        assertEquals(1700, joltDifference)
    }

    @Test
    fun second() {
        val distinctWays = day10.second(
            """
            16
            10
            15
            5
            1
            11
            7
            19
            6
            12
            4
        """.trimIndent()
        )

        assertEquals(8, distinctWays)
    }

    @Test
    fun secondBig() {
        val joltDifference = day10.second(
            """
            28
            33
            18
            42
            31
            14
            46
            20
            48
            47
            24
            23
            49
            45
            19
            38
            39
            11
            1
            32
            25
            35
            8
            17
            7
            9
            4
            2
            34
            10
            3
        """.trimIndent()
        )

        assertEquals(19208, joltDifference)
    }

    @Test
    fun secondFile() {
        val joltDifference = day10.second(getResourceAsText("day10.input"))

        assertEquals(12401793332096, joltDifference)
    }
}