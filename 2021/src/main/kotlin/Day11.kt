class Day11 {

    fun first(input: String): Int {
        val map = parseInput(input)

        return map.getNumFlashes()
    }

    fun second(input: String): Int {
        val map = parseInput(input)

        return map.getStepAllFlash()
    }

    private fun parseInput(input: String): OctopusMap {
        val lines = input.lines()
        val numRows = lines.size
        val numCols = lines.first().length
        val map = Array(numRows + 2) { Array(numCols + 2) { Octopus.EMPTY } }

        for (i in 1..numRows) {
            for (j in 1..numCols) {
                map[i][j] = lines[i - 1][j - 1].toOctopus()
            }
        }

        return OctopusMap(map)
    }

    class Octopus(var energy: Int) {
        fun increaseEnergy(): Boolean {
            if (energy < MAX_ENERGY) {
                energy++
                if (energy == MAX_ENERGY) return true
            }
            return false
        }

        fun reset() {
            if (energy == MAX_ENERGY) energy = 0
        }

        companion object {
            val EMPTY = Octopus(energy = 10)
            const val MAX_ENERGY = 10
        }
    }

    class OctopusMap(private val map: Array<Array<Octopus>>) {
        private val numRows = map.size - 2
        private val numCols = map.first().size - 2

        fun getNumFlashes(): Int {
            val tempMap: Array<Array<Octopus>> = map.deepCopy()

            return (1..100).sumOf { increaseStep(tempMap) }
        }

        fun getStepAllFlash(): Int {
            val tempMap: Array<Array<Octopus>> = map.deepCopy()

            generateSequence(seed = 1, Int::inc).forEach { step ->
                increaseStep(tempMap)
                if (tempMap.isAllReset()) return step
            }
            throw IllegalStateException("Not reachable")
        }

        private fun increaseStep(tempMap: Array<Array<Octopus>>): Int {
            fun increaseEnergy(i: Int, j: Int): Int {
                if (tempMap[i][j].energy == Octopus.MAX_ENERGY) return 0

                return if (tempMap[i][j].increaseEnergy()) {
                    1 + Point(i, j).adjacentLocations.sumOf { next -> increaseEnergy(next.i, next.j) }
                } else 0
            }

            val flashes = (1..numRows).sumOf { i ->
                (1..numCols).sumOf { j ->
                    increaseEnergy(i, j)
                }
            }

            for (i in 1..numRows) {
                for (j in 1..numCols) {
                    tempMap[i][j].reset()
                }
            }

            return flashes
        }

        data class Point(val i: Int, val j: Int)

        private val Point.up get() = Point(i - 1, j)
        private val Point.down get() = Point(i + 1, j)
        private val Point.left get() = Point(i, j - 1)
        private val Point.right get() = Point(i, j + 1)
        private val Point.upRight get() = Point(i + 1, j + 1)
        private val Point.downRight get() = Point(i - 1, j + 1)
        private val Point.downLeft get() = Point(i - 1, j - 1)
        private val Point.upLeft get() = Point(i + 1, j - 1)
        private val Point.adjacentLocations get() = listOf(up, upRight, right, downRight, down, downLeft, left, upLeft)

        private fun Array<Array<Octopus>>.deepCopy() = Array(size) { get(it).copyOf() }
        private fun Array<Array<Octopus>>.isAllReset() =
            all { it.all { octopus -> octopus.energy == 0 || octopus == Octopus.EMPTY } }
    }

    private fun Char.toOctopus(): Octopus = Octopus(digitToInt())
}

