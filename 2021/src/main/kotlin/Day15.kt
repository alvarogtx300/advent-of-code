import java.util.*
import Day15.RiskMap.Point as Point

class Day15 {

    fun first(input: String): Int {
        val riskMap = parseInput(input)

        return riskMap.findLowestRisk()
    }

    fun second(input: String): Int {
        val bigRiskMap = parseInput(input).generateBigMap()

        return bigRiskMap.findLowestRisk()
    }

    private fun parseInput(input: String): RiskMap {
        val lines = input.lines()
        val numRows = lines.size
        val numCols = lines.first().length
        val map = Array(numRows) { Array(numCols) { Node.EMPTY } }

        for (i in map.indices) {
            for (j in map.first().indices) {
                map[i][j] = Node(lines[i][j].digitToInt(), Point(i, j))
            }
        }

        return RiskMap(map)
    }

    class RiskMap(private val map: Array<Array<Node>>) {
        private val numRows = map.size
        private val numCols = map.first().size

        init {
            map[0][0].risk = 0
        }

        fun findLowestRisk(): Int {
            val queue = PriorityQueue(listOf(map[0][0]))

            while (queue.isNotEmpty()) {
                val actual = queue.poll()

                actual.point.adjacentLocations
                    .map { map[it.i][it.j] }
                    .forEach { nextNode ->
                        val nextRisk = actual.risk + nextNode.value
                        if (nextNode.risk > nextRisk) {
                            nextNode.risk = nextRisk
                            queue.add(nextNode)
                        }
                    }
            }

            return map[numRows - 1][numCols - 1].risk
        }

        fun generateBigMap(): RiskMap {
            val bigMap = Array(numRows * FULL_SIZE) { Array(numCols * FULL_SIZE) { Node.EMPTY } }

            for (i in bigMap.indices) {
                for (j in bigMap.first().indices) {
                    val ni = i % numRows
                    val nj = j % numCols
                    bigMap[i][j] = Node(value = (map[ni][nj].value + (i / numRows) + (j / numCols)).mod(9), Point(i, j))
                }
            }

            return RiskMap(bigMap)
        }

        data class Point(val i: Int, val j: Int)

        private val Point.right get() = Point(i, j + 1)
        private val Point.down get() = Point(i + 1, j)
        private val Point.up get() = Point(i - 1, j)
        private val Point.left get() = Point(i, j - 1)
        private val Point.adjacentLocations
            get() = listOf(right, down, up, left)
                .filter { it.i in (0 until numRows) && it.j in (0 until numCols) }

        private fun Int.mod(mod: Int) = if (this % mod == 0) mod else this % mod

        companion object {
            const val FULL_SIZE = 5
        }
    }

    data class Node(val value: Int, val point: Point, var risk: Int = Int.MAX_VALUE) : Comparable<Node> {
        override fun compareTo(other: Node) = risk.compareTo(other.risk)

        companion object {
            val EMPTY = Node(-1, Point(-1, -1))
        }
    }
}
