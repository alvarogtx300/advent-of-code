class Day12 {

    fun first(input: String): Int {
        val caveMap = parseInput(input)

        return caveMap.getNumPaths()
    }

    fun second(input: String): Int {
        val caveMap = parseInput(input)

        return caveMap.getNumPaths(visitSmallTwice = true)
    }

    private fun parseInput(input: String) = CaveMap().apply {
        input.lines()
            .map { it.split("-") }
            .forEach { (start, end) -> addCaveConnection(start, end) }
    }

    class CaveMap(private val caves: MutableMap<String, Cave> = mutableMapOf()) {
        private val startCave get() = caves.getValue("start")

        fun addCaveConnection(start: String, end: String) {
            val startCave = caves.getOrPut(start) { Cave(start) }
            val endCave = caves.getOrPut(end) { Cave(end) }

            startCave.addAdjacentCave(endCave)
            endCave.addAdjacentCave(startCave)
        }

        fun getNumPaths(visitSmallTwice: Boolean = false): Int {
            val paths = mutableListOf<String>()

            fun visit(cave: Cave, path: List<Cave> = emptyList()) {
                if (cave.isEnd) {
                    paths.add((path + cave).joinToString(separator = ","))
                    return
                }
                if (!canVisit(cave, path, visitSmallTwice)) return

                cave.adjacentCaves.forEach { next -> visit(next, path + cave) }
            }

            visit(startCave)

            return paths.size
        }

        private fun canVisit(cave: Cave, path: List<Cave>, visitSmallTwice: Boolean): Boolean {
            val smallCaves = path.filter(Cave::isSmall).groupBy(Cave::name)

            return when {
                cave.isBig -> true
                cave.isStart -> cave !in path
                cave.name !in smallCaves -> true
                visitSmallTwice -> (smallCaves.maxOfOrNull { it.value.size } ?: 0) < 2
                else -> false
            }
        }
    }

    class Cave(val name: String, val adjacentCaves: MutableList<Cave> = mutableListOf()) {
        val isSmall = name.first().isLowerCase()
        val isBig = name.first().isUpperCase()
        val isStart = name == "start"
        val isEnd = name == "end"

        fun addAdjacentCave(cave: Cave) {
            adjacentCaves.add(cave)
        }

        override fun toString() = name
    }
}
