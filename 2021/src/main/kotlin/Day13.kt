class Day13 {

    fun first(input: String): Int {
        val paper = parseInput(input)

        paper.fold(paper.folds.first())
        return paper.getVisibleDots()
    }

    fun second(input: String): String {
        val paper = parseInput(input)

        paper.folds.forEach { paper.fold(it) }
        return paper.print()
    }

    private fun parseInput(input: String): Paper {
        val dotList = input.lines()
            .filter { it.firstOrNull()?.isDigit() ?: false }
            .map { it.split(",") }
            .map { (first, second) -> Pair(first.toInt(), second.toInt()) }
        val (maxX, maxY) = with(dotList.unzip()) { first.maxOrNull()!! to second.maxOrNull()!! }

        val folds = input.lines()
            .filter { it.startsWith("fold") }
            .map { it.substringAfter("fold along ").split("=") }
            .map { (axis, position) -> Fold(Axis.valueOf(axis.uppercase()), position.toInt()) }

        val dots = Array(maxY + 1) { BooleanArray(maxX + 1) }
        dotList.forEach { (x, y) -> dots[y][x] = true }

        return Paper(dots, folds)
    }

    class Paper(private var dots: Array<BooleanArray>, val folds: List<Fold>) {

        fun getVisibleDots(): Int = dots.sumOf { it.sumOf { dot -> dot.toInt() } }

        fun print() = buildString {
            dots.forEach { row ->
                row.forEach { dot ->
                    append(if (dot) "#" else ".")
                }
                appendLine()
            }
        }

        fun fold(fold: Fold) {
            when (fold.axis) {
                Axis.X -> foldLeft(x = fold.position)
                Axis.Y -> foldUp(y = fold.position)
            }
        }

        private fun foldUp(y: Int) {
            val upper = dots.take(y).toTypedArray()
            val lower = dots.takeLast(dots.size - y - 1)
            var flippedLower = lower.asReversed().toTypedArray()

            while (upper.size > flippedLower.size) {
                flippedLower = flippedLower.addEmptyRow()
            }

            dots = upper overlap flippedLower
        }

        private fun foldLeft(x: Int) {
            val left = dots.map { row -> row.take(x).toBooleanArray() }.toTypedArray()
            val right = dots.map { row -> row.takeLast(row.size - x - 1) }
            var flippedRight = right.map { row -> row.asReversed().toBooleanArray() }.toTypedArray()

            while (left.size > flippedRight.size) {
                flippedRight = flippedRight.addEmptyCol()
            }

            dots = left overlap flippedRight
        }

        private infix fun Array<BooleanArray>.overlap(other: Array<BooleanArray>): Array<BooleanArray> {
            for (y in indices) {
                for (x in first().indices) {
                    this[y][x] = this[y][x] || other[y][x]
                }
            }
            return this
        }

        private fun Array<BooleanArray>.addEmptyRow() = Array(1) { BooleanArray(first().size) } + this
        private fun Array<BooleanArray>.addEmptyCol() = map { row -> booleanArrayOf(false) + row }.toTypedArray()
        private fun Boolean.toInt() = if (this) 1 else 0
    }

    class Fold(val axis: Axis, val position: Int)
    enum class Axis { X, Y }
}
