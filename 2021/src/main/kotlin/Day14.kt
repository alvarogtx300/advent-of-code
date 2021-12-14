class Day14 {

    fun first(input: String): Long {
        val polymer = parseInput(input)

        for (step in 1..10) {
            polymer.transform()
        }

        return polymer.calculateQuantity()
    }

    fun second(input: String): Long {
        val polymer = parseInput(input)

        for (step in 1..40) {
            polymer.transform()
        }

        return polymer.calculateQuantity()
    }

    private fun parseInput(input: String): Polymer {
        val template = input.lines().first()
        val rules = input.lines().drop(2)
            .map { it.split(" -> ") }
            .associate { (first, second) -> first to second }

        return Polymer(template, rules)
    }

    class Polymer(private val template: String, private val rules: Map<String, String>) {
        private var numPairs = rules.keys.associateWithTo(mutableMapOf()) { 0L }.apply {
            template.zipWithNext(Char::plus).forEach { pair ->
                merge(pair, 1, Long::plus)
            }
        }.toMap()

        fun transform() {
            numPairs = buildMap {
                numPairs.forEach { (pair, count) ->
                    val newChar = rules.getValue(pair)
                    merge(pair.first() + newChar, count, Long::plus)
                    merge(newChar + pair.last(), count, Long::plus)
                }
            }
        }

        fun calculateQuantity(): Long {
            val sizes = numPairs.flatMap { (pair, count) -> listOf(pair.first() to count, pair.last() to count) }
                .groupBy({ it.first }, { it.second })
                .mapValues { it.value.sum() / 2 }
                .map { (char, count) ->
                    if (char == template.last() || char == template.first()) count + 1
                    else count
                }

            return sizes.maxOrNull()!! - sizes.minOrNull()!!
        }
    }
}

private operator fun Char.plus(other: Char) = this + other.toString()
