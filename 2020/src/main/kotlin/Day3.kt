val Pair<Int, Int>.x get() = this.first
val Pair<Int, Int>.y get() = this.second

class Day3 {

    fun first(input: String): Int {
        val treeMap = parseInput(input)
        val width = treeMap.first().size

        var numTrees = 0
        var i = 0
        for (chars in treeMap) {
            if (chars.elementAt(i) == '#') numTrees++
            i = (i + 3) % width
        }

        return numTrees
    }

    fun second(input: String): Int {
        val treeMap = parseInput(input)
        val width = treeMap.first().size
        val positions = listOf(
            1 to 1,
            3 to 1,
            5 to 1,
            7 to 1,
            1 to 2,
        )

        val numTrees = MutableList(positions.size) { 0 }
        for ((positionIndex, position) in positions.withIndex()) {
            var i = 0
            for ((index, chars) in treeMap.withIndex()) {
                if (index % position.y == 0) {
                    if (chars.elementAt(i) == '#') numTrees[positionIndex]++
                    i = (i + position.x) % width
                }
            }
        }

        return numTrees.reduce { acc, i -> acc * i }
    }

    private fun parseInput(input: String): Array<CharArray> =
        input.split("\n", "\r\n").map { it.toCharArray() }.toTypedArray()
}