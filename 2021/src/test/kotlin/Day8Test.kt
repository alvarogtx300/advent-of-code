import kotlin.test.Test
import kotlin.test.assertEquals

class Day8Test {
    private val day8 = Day8()

    @Test
    fun first() {
        val result = day8.first(TEST_INPUT)

        assertEquals(26, result)
    }

    @Test
    fun firstFile() {
        val result = day8.first(getResourceAsText("day8.input"))

        assertEquals(301, result)
    }

    @Test
    fun second() {
        val result = day8.second(TEST_INPUT)

        assertEquals(61229, result)
    }

    @Test
    fun secondFile() {
        val result = day8.second(getResourceAsText("day8.input"))

        assertEquals(908067, result)
    }

    companion object {
        val TEST_INPUT = """
            be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
            edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
            fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
            fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
            aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
            fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
            dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
            bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
            egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
            gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce
        """.trimIndent()
    }
}