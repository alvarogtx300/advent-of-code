import kotlin.math.roundToInt

class Day18 {

    fun first(input: String): Int {
        val reduced = input.lines()
            .reduce { acc, s ->
                reduce(sum(acc, s))
            }

        val snailNumber = parseSnailNumber(reduced)

        return snailNumber.getMagnitude()
    }

    fun second(input: String): Int {
        return input.lines().maxOf { a ->
            input.lines().maxOf { b ->
                parseSnailNumber(reduce(sum(a, b))).getMagnitude()
            }
        }
    }

    private fun parseSnailNumber(input: String): SnailNumber {
        val stack = mutableListOf<SnailNumber>()

        for (c in input) {
            if (c.isDigit()) stack.push(RegularNumber(c.digitToInt()))
            if (c == ']') stack.push(PairNumber(second = stack.pop(), first = stack.pop()))
        }

        return stack.pop()
    }

    private fun reduce(str: String): String = when {
        findIndexExplode(str) != null -> reduce(explode(str))
        findIndexSplit(str) != null -> reduce(split(str))
        else -> str
    }

    private fun findIndexExplode(number: String): Int? {
        var level = 0
        for ((index, c) in number.withIndex()) {
            if (c == '[') level++
            if (c == ']') {
                if (level > 4) {
                    return number.take(index).dropLastWhile { it != '[' }.length
                }
                level--
            }
        }
        return null
    }

    private fun findIndexSplit(str: String): Int? {
        str.zipWithNext().forEachIndexed { index, (first, second) ->
            if (first.isDigit() && second.isDigit()) {
                return index
            }
        }
        return null
    }

    private fun explode(number: String): String {
        val index = findIndexExplode(number)
        if (index != null) {
            val firstPart = number.substring(0, index - 1)
            val first = number.substring(index).takeWhile { it.isDigit() }.toInt()
            val newFirst = sumToString(firstPart.reversed(), first, reversed = true).reversed()

            val second = number.substring(index + first.toString().length + 1).takeWhile { it.isDigit() }.toInt()
            val secondPart = number.substring(index + first.toString().length + second.toString().length + 2)
            val newSecond = sumToString(secondPart, second)

            return "${newFirst}0${newSecond}"
        }

        return number
    }

    private fun split(str: String): String {
        findIndexSplit(str)?.let { index ->
            val num = str.substring(index, index + 2).toInt()
            val first = num / 2
            val second = (num / 2.0).roundToInt()

            return str.replaceRange(index, index + 2, "[$first,$second]")
        }

        return str
    }

    private fun sum(first: String, second: String) = "[$first,$second]"

    private fun sumToString(str: String, num: Int, reversed: Boolean = false): String {
        for ((index, c) in str.withIndex()) {
            if (c.isDigit()) {
                val firstPart = str.substring(0, index)
                val number = str.substring(index).takeWhile { it.isDigit() }
                val secondPart = str.substring(index + number.length)
                val newNum = if (reversed) {
                    (number.reversed().toInt() + num).toString().reversed()
                } else {
                    (number.toInt() + num).toString()
                }

                return "${firstPart}${newNum}${secondPart}"
            }
        }

        return str
    }

    interface SnailNumber {
        fun getMagnitude(): Int
    }

    class PairNumber(private val first: SnailNumber, private val second: SnailNumber) : SnailNumber {
        override fun getMagnitude() = first.getMagnitude() * 3 + second.getMagnitude() * 2
    }

    class RegularNumber(private val num: Int) : SnailNumber {
        override fun getMagnitude() = num
    }

    private fun <T> MutableList<T>.push(item: T) = add(item)
    private fun <T> MutableList<T>.pop(): T = removeLast()
}
