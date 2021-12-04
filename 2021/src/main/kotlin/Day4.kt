class Day4 {

    fun first(input: String): Int {
        val numbers = parseNumbers(input)
        val boards = parseBoards(input)
        lateinit var winnerBoard: Board

        run loop@{
            numbers.forEach { n ->
                boards.forEach { board ->
                    board.mark(n)
                    if (board.isBingo()) {
                        winnerBoard = board
                        return@loop
                    }
                }
            }
        }

        return winnerBoard.getUnmarkedNumbers().sum() * winnerBoard.lastNumber
    }

    fun second(input: String): Int {
        val numbers = parseNumbers(input)
        val boards = parseBoards(input)
        val winnerBoards = mutableSetOf<Board>()
        lateinit var winnerBoard: Board

        run loop@{
            numbers.forEach { n ->
                boards.forEach { board ->
                    board.mark(n)
                    if (board.isBingo()) {
                        if (!winnerBoards.contains(board) && winnerBoards.size == boards.size - 1) {
                            winnerBoard = board
                            return@loop
                        }
                        winnerBoards.add(board)
                    }
                }
            }
        }

        return winnerBoard.getUnmarkedNumbers().sum() * winnerBoard.lastNumber
    }

    private fun parseNumbers(input: String): List<Int> = input.lines().first()
        .split(",").map(String::toInt)

    private fun parseBoards(input: String): List<Board> = input.lines().drop(1)
        .chunked(size = 6).map { Board(it.drop(1)) }
}

class Board(rawBoard: List<String>) {
    private val board: Array<IntArray> = rawBoard.map {
        it.windowed(size = 2, step = 3).map { num -> num.trim().toInt() }.toIntArray()
    }.toTypedArray()
    private val markedBoard = Array(5) { BooleanArray(5) }
    var lastNumber = 0

    fun mark(n: Int) {
        val index = findIndexOf(n)
        index?.let { markedBoard[it.first][it.second] = true }
        lastNumber = n
    }

    fun isBingo(): Boolean = markedBoard.isRowCompleted() || markedBoard.isColCompleted()

    fun getUnmarkedNumbers(): List<Int> {
        val numbers = mutableListOf<Int>()
        markedBoard.forEachIndexed { i, row ->
            row.forEachIndexed { j, marked ->
                if (!marked) {
                    numbers.add(board[i][j])
                }
            }
        }
        return numbers
    }

    private fun findIndexOf(n: Int): Pair<Int, Int>? {
        board.forEachIndexed { i, row ->
            row.forEachIndexed { j, num ->
                if (num == n) {
                    return Pair(i, j)
                }
            }
        }
        return null
    }

    private fun Array<BooleanArray>.isRowCompleted() = any { row -> row.all { it } }
    private fun Array<BooleanArray>.isColCompleted() = indices.any { i -> map { it[i] }.all { it } }
}