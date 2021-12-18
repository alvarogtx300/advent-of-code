import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sqrt

class Day17 {

    fun first(input: String): Int {
        val (_, yRange) = parseInput(input)

        val maxY = calcMaxY(yRange)
        return calcMaxHeight(maxY)
    }

    fun second(input: String): Int {
        val (xRange, yRange) = parseInput(input)

        val maxY = calcMaxY(yRange)
        val minY = yRange.first

        val maxX = xRange.last
        val minX = calcMinX(xRange.first)

        return (minX..maxX).sumOf { x ->
            (minY..maxY).count { y ->
                hitTarget(x, y, xRange, yRange)
            }
        }
    }

    private fun calcMaxY(yRange: IntRange) = abs(yRange.first) - 1
    private fun calcMaxHeight(velY: Int) = velY * (velY + 1) / 2
    private fun calcMinX(x: Int) = ((sqrt(8.0 * x + 1) - 1) / 2).roundToInt()

    private fun hitTarget(vx: Int, vy: Int, xRange: IntRange, yRange: IntRange): Boolean {
        var x = 0
        var y = 0
        var nVx = vx
        var nVy = vy

        while (x <= xRange.last && y >= yRange.first) {
            if (x in xRange && y in yRange) return true

            y += nVy--
            x += if (nVx == 0) 0 else nVx--
        }

        return false
    }

    private fun parseInput(input: String): Pair<IntRange, IntRange> =
        Regex("""x=(\d+)..(\d+), y=(-\d+)..(-\d+)""").find(input)!!.destructured
            .let { (x1, x2, y1, y2) -> x1.toInt()..x2.toInt() to y1.toInt()..y2.toInt() }
}
