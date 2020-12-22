class Day4 {

    fun first(input: String): Int {
        val passports = parseInput(input)

        return passports.count { it.areAllFieldsPresent() }
    }

    fun second(input: String): Int {
        val passports = parseInput(input)

        return passports.count { it.isValid() }
    }

    private fun parseInput(input: String): List<Passport> = input
        .split("\n\n", "\r\n\r\n")
        .map { it.split(Regex("""\R| """)) }
        .map { it.toPassport() }

    private fun List<String>.toPassport(): Passport = Passport(map {
        it.split(":").run { Pair(this[0], this[1]) }
    }.toMap().withDefault { null })
}

class Passport(map: Map<String, String?>) {
    private val byr: String? by map
    private val iyr: String? by map
    private val eyr: String? by map
    private val hgt: String? by map
    private val hcl: String? by map
    private val ecl: String? by map
    private val pid: String? by map
    private val cid: String? by map

    fun areAllFieldsPresent(): Boolean = byr != null
        && iyr != null
        && eyr != null
        && hgt != null
        && hcl != null
        && ecl != null
        && pid != null

    fun isValid(): Boolean {
        if (!areAllFieldsPresent()) return false

        try {
            return byr!!.toInt() in 1920..2002
                && iyr!!.toInt() in 2010..2020
                && eyr!!.toInt() in 2020..2030
                && Regex("""(\d+)(cm|in)""").find(hgt!!)!!.groupValues.let {
                when (it[2]) {
                    "cm" -> it[1].toInt() in 150..193
                    "in" -> it[1].toInt() in 59..76
                    else -> false
                }
            }
                && hcl!!.matches(Regex("""#[0-9a-f]{6}"""))
                && ecl in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
                && pid!!.matches(Regex("""\d{9}"""))
        } catch (e: Exception) {
            return false
        }
    }
}