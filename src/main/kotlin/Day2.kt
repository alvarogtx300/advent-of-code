class Day2 {

    fun first(input: String): Int {
        val passwordPolicies = parseInput(input)

        var valid = 0
        for ((min, max, letter, password) in passwordPolicies) {
            val numLetters = password.count { it == letter }
            if (numLetters in min..max) {
                valid++
            }
        }

        return valid
    }

    fun second(input: String): Int {
        val passwordPolicies = parseInput(input)

        var valid = 0
        for (passwordPolicy in passwordPolicies) {
            with(passwordPolicy) {
                if ((password.elementAt(min - 1) == letter) xor (password.elementAt(max - 1) == letter)) {
                    valid++
                }
            }
        }

        return valid
    }

    private fun parseInput(input: String): List<PasswordPolicy> =
        input.split("\n", "\r\n").map { Regex("""(\d+)-(\d+) (\w): (\w+)""").find(it)!!.groupValues.toPasswordPolicy() }

    private fun List<String>.toPasswordPolicy(): PasswordPolicy =
        PasswordPolicy(
            min = get(1).toInt(),
            max = get(2).toInt(),
            letter = get(3).single(),
            password = get(4)
        )
}

data class PasswordPolicy(
    val min: Int,
    val max: Int,
    val letter: Char,
    val password: String
)