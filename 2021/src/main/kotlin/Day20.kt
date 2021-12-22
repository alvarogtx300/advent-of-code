class Day20 {

    fun first(input: String): Int {
        val image = parseInput(input, enhanceSteps = 2)
        image.applyFilter()

        return image.countPixels()
    }

    fun second(input: String): Int {
        val image = parseInput(input, enhanceSteps = 50)
        image.applyFilter()

        return image.countPixels()
    }

    private fun parseInput(input: String, enhanceSteps: Int): Image {
        val offset = enhanceSteps + 1
        val filter = input.lines().first().map { it.toImgPixel() }.joinToString(separator = "")

        val lines = input.lines().drop(2)
        val numRows = lines.size
        val numCols = lines.first().length
        val img = Array(numRows + offset * 2) { IntArray(numCols + offset * 2) { 0 } }

        for (i in offset until numRows + offset) {
            for (j in offset until numCols + offset) {
                img[i][j] = lines[i - offset][j - offset].toImgPixel()
            }
        }

        return Image(img, filter, enhanceSteps)
    }


    class Image(private var img: Array<IntArray>, private val filter: String, private val enhanceSteps: Int) {

        fun applyFilter() {
            for (step in 1..enhanceSteps) {
                val imgFiltered = img.deepCopy()

                for (x in img.indices) {
                    for (y in img.first().indices) {
                        // Infinite border
                        if (x == 0 || y == 0 || x == img.size - 1 || y == img.size - 1) {
                            when (img[x][y]) {
                                0 -> imgFiltered[x][y] = filter.first().digitToInt()
                                1 -> imgFiltered[x][y] = filter.last().digitToInt()
                            }
                        } else {
                            imgFiltered[x][y] = filter[getPixelValueAround(x, y)].digitToInt()
                        }
                    }
                }
                img = imgFiltered
            }
        }

        fun countPixels() = img.sumOf { row -> row.sum() }

        private fun getPixelValueAround(x: Int, y: Int) = buildString {
            for (i in -1..1) {
                for (j in -1..1) {
                    append(img[x + i][y + j])
                }
            }
        }.toInt(radix = 2)

        @Suppress("unused")
        private fun print() = img.joinToString(separator = "\n") { row ->
            row.joinToString(separator = "") { if (it == 1) "#" else "." }
        }

        private fun Array<IntArray>.deepCopy() = Array(size) { get(it).copyOf() }
    }

    private fun Char.toImgPixel() = if (this == '#') 1 else 0
}
