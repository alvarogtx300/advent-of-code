import kotlin.test.Test
import kotlin.test.assertEquals

class Day14Test {
    private val day14 = Day14()

    @Test
    fun first() {
        val result = day14.first(TEST_INPUT)

        assertEquals(1588, result)
    }

    @Test
    fun firstFile() {
        val result = day14.first(getResourceAsText("day14.input"))

        assertEquals(2360, result)
    }

    @Test
    fun second() {
        val result = day14.second(TEST_INPUT)

        assertEquals(2188189693529, result)
    }

    @Test
    fun secondFile() {
        val result = day14.second(getResourceAsText("day14.input"))

        assertEquals(2967977072188, result)
    }

    companion object {
        val TEST_INPUT = """
            NNCB
            
            CH -> B
            HH -> N
            CB -> H
            NH -> C
            HB -> C
            HC -> B
            HN -> C
            NN -> C
            BH -> H
            NC -> B
            NB -> B
            BN -> B
            BB -> N
            BC -> B
            CC -> N
            CN -> C
        """.trimIndent()
    }
}