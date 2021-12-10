class Day10 {

    fun first(input: String): Int {
        val chunks = parseInput(input)

        return chunks.sumOf { it.getFirstIllegal() }
    }

    fun second(input: String): Long {
        val chunks = parseInput(input)

        return chunks.mapNotNull(Chunk::getIncompleteLine).sorted().let { it[it.size / 2] }
    }

    private fun parseInput(input: String): List<Chunk> = input.lines().map { Chunk(it.toList()) }

    class Chunk(private val chunk: List<Char>) {
        fun getFirstIllegal(): Int {
            val stack = mutableListOf<Char>()

            for (c in chunk) {
                if (c.isOpen()) stack.push(c)
                else if (c.close != stack.pop()) return points.getValue(c)
            }

            return 0
        }

        fun getIncompleteLine(): Long? {
            if (getFirstIllegal() != 0) return null
            val stack = mutableListOf<Char>()

            for (c in chunk) {
                if (c.isOpen()) stack.push(c)
                else stack.pop()
            }

            return stack.foldRight(0) { c, acc: Long -> 5 * acc + pointsIncomplete.getValue(c) }
        }

        companion object {
            private val chunks = mapOf(
                ')' to '(',
                ']' to '[',
                '}' to '{',
                '>' to '<'
            )
            private val points = mapOf(
                ')' to 3,
                ']' to 57,
                '}' to 1197,
                '>' to 25137
            )
            private val pointsIncomplete = mapOf(
                '(' to 1,
                '[' to 2,
                '{' to 3,
                '<' to 4
            )
            private val Char.close get() = chunks[this]
            private fun Char.isOpen() = this in chunks.values
        }

        private fun MutableList<Char>.push(item: Char) = add(item)
        private fun MutableList<Char>.pop(): Char? = if (isNotEmpty()) removeLast() else null
    }
}
