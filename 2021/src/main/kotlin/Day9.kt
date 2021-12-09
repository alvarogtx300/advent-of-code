class Day9 {

    fun first(input: String): Int {
        val map = parseInput(input)

        return map.getLowPoints().map(Int::inc).sum()
    }

    fun second(input: String): Int {
        val map = parseInput(input)

        return map.getBasins().sortedDescending().take(3).reduce(Int::times)
    }

    private fun parseInput(input: String): HeightMap {
        val lines = input.lines()
        val numRows = lines.size
        val numCols = lines.first().length
        val map = Array(numRows + 2) { IntArray(numCols + 2) { HeightMap.WALL } }

        for (i in 1..numRows) {
            for (j in 1..numCols) {
                map[i][j] = lines[i - 1][j - 1].digitToInt()
            }
        }

        return HeightMap(map)
    }
}

class HeightMap(private val map: Array<IntArray>) {
    private val numRows = map.size - 2
    private val numCols = map.first().size - 2

    fun getLowPoints(): List<Int> = buildList {
        for (i in 1..numRows) {
            for (j in 1..numCols) {
                if (isLowerPoint(i, j)) {
                    add(map[i][j])
                }
            }
        }
    }

    fun getBasins(): List<Int> {
        val tempMap: Array<IntArray> = map.deepCopy()

        fun findBasin(i: Int, j: Int): Int? {
            if (tempMap[i][j] == WALL) return null

            tempMap[i][j] = WALL // Visited
            return 1 + Point(i, j).adjacentLocations.sumOf { next ->
                findBasin(next.i, next.j) ?: 0
            }
        }

        return buildList {
            for (i in 1..numRows) {
                for (j in 1..numCols) {
                    findBasin(i, j)?.let { add(it) }
                }
            }
        }
    }

    private fun isLowerPoint(i: Int, j: Int): Boolean {
        val point = map[i][j]
        return Point(i, j).adjacentLocationValues.all { point < it }
    }

    data class Point(val i: Int, val j: Int)

    private val Point.up get() = Point(i - 1, j)
    private val Point.down get() = Point(i + 1, j)
    private val Point.left get() = Point(i, j - 1)
    private val Point.right get() = Point(i, j + 1)
    private val Point.adjacentLocations get() = listOf(up, right, down, left)

    private val Point.upValue get() = map[i - 1][j]
    private val Point.downValue get() = map[i + 1][j]
    private val Point.leftValue get() = map[i][j - 1]
    private val Point.rightValue get() = map[i][j + 1]
    private val Point.adjacentLocationValues get() = listOf(upValue, rightValue, downValue, leftValue)

    private fun Array<IntArray>.deepCopy() = Array(size) { get(it).copyOf() }

    companion object {
        const val WALL = 9
    }
}
