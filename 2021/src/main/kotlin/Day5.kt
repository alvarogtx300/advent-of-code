class Day5 {

    fun first(input: String): Int {
        val lines = parseInput(input)

        return lines.flatMap(Line::asPoints).groupBy { it }.count { it.value.size > 1 }
    }

    fun second(input: String): Int {
        val lines = parseInput(input)

        return lines.flatMap { it.asPoints(withDiagonals = true) }.groupBy { it }.count { it.value.size > 1 }
    }

    private fun parseInput(input: String): List<Line> = input.lines()
        .map { it.split(" -> ").map { it.split(",") }.map { (x, y) -> Point(x.toInt(), y.toInt()) } }
        .map { (p1, p2) -> Line(p1, p2) }
}

data class Point(val x: Int, val y: Int)

data class Line(val p1: Point, val p2: Point) {

    fun asPoints(withDiagonals: Boolean = false): List<Point> = when {
        isHorizontal() -> range(p1.x, p2.x).map { x -> Point(x, p1.y) }
        isVertical() -> range(p1.y, p2.y).map { y -> Point(p1.x, y) }
        withDiagonals -> (range(p1.x, p2.x) zip range(p1.y, p2.y)).map { (x, y) -> Point(x, y) }
        else -> emptyList()
    }

    private fun isHorizontal() = p1.y == p2.y
    private fun isVertical() = p1.x == p2.x
}

private fun range(a: Int, b: Int) = if (a <= b) a.rangeTo(b) else a.downTo(b)
