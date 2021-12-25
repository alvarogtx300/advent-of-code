class Day25 {

    fun first(input: String): Int {
        val cucumberMap = parseInput(input)

        var step = 1
        while (!cucumberMap.isBlocked()) {
            cucumberMap.moveStep()
            step++
        }

        return step
    }

    private fun parseInput(input: String) = CucumberMap(input.lines().map(String::toCharArray).toTypedArray())

    class CucumberMap(var map: Array<CharArray>) {
        private val rows = map.size
        private val cols = map.first().size

        fun moveStep() {
            moveRight()
            moveDown()
        }

        fun isBlocked(): Boolean {
            map.forEachIndexed { i, row ->
                row.forEachIndexed { j, c ->
                    if (c == '>') {
                        val nj = (j + 1) % cols
                        if (map[i][nj] == '.') {
                            return false
                        }
                    } else if (c == 'v') {
                        val ni = (i + 1) % rows
                        if (map[ni][j] == '.') {
                            return false
                        }
                    }
                }
            }
            return true
        }

        private fun moveRight() {
            val newMap = map.deepCopy()

            map.forEachIndexed { i, row ->
                row.forEachIndexed { j, c ->
                    if (c == '>') {
                        val nj = (j + 1) % cols
                        if (map[i][nj] == '.') {
                            newMap[i][nj] = map[i][j]
                            newMap[i][j] = '.'
                        }
                    }
                }
            }
            map = newMap
        }

        private fun moveDown() {
            val newMap = map.deepCopy()

            map.forEachIndexed { i, row ->
                row.forEachIndexed { j, c ->
                    if (c == 'v') {
                        val ni = (i + 1) % rows
                        if (map[ni][j] == '.') {
                            newMap[ni][j] = map[i][j]
                            newMap[i][j] = '.'
                        }
                    }
                }
            }
            map = newMap
        }

        private fun Array<CharArray>.deepCopy() = Array(size) { get(it).copyOf() }
    }
}
