typealias Command = Pair<String, Int>

val Command.direction get() = first
val Command.amount get() = second

class Day2 {

    fun first(input: String): Int {
        val commands = parseInput(input)

        var horizontal = 0
        var depth = 0

        commands.forEach {
            when (it.direction) {
                "forward" -> horizontal += it.amount
                "down" -> depth += it.amount
                "up" -> depth -= it.amount
            }
        }

        return horizontal * depth
    }

    fun second(input: String): Int {
        val commands = parseInput(input)

        var aim = 0
        var horizontal = 0
        var depth = 0

        commands.forEach {
            when (it.direction) {
                "forward" -> {
                    horizontal += it.amount
                    depth += aim * it.amount
                }
                "down" -> aim += it.amount
                "up" -> aim -= it.amount
            }
        }

        return horizontal * depth
    }

    private fun parseInput(input: String): List<Command> = input.lines()
        .map { it.split(" ") }
        .map { (direction, amount) -> Command(direction, amount.toInt()) }
}
