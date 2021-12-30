import Day24.Type.Companion.toType
import java.lang.IllegalStateException

class Day24 {

    fun first(input: String): Long {
        val instructions = parseInput(input)

        return Alu().executeProgram(instructions)
            .filter { it.z == 0 }
            .maxOf(AluState::max)
    }

    fun second(input: String): Long {
        val instructions = parseInput(input)

        return Alu().executeProgram(instructions)
            .filter { it.z == 0 }
            .minOf(AluState::min)
    }

    private fun parseInput(input: String): List<Instruction> = input.lines().map { line ->
        line.split(" ").let { Instruction(it[0].toType(), it[1], it.getOrNull(2)) }
    }

    enum class Type(private val value: String) {
        INP("inp"),
        ADD("add"),
        MUL("mul"),
        DIV("div"),
        MOD("mod"),
        EQL("eql");

        companion object {
            private val map = values().associateBy(Type::value)
            fun String.toType() = map[this] ?: error("Invalid type: $this")
        }
    }

    class Instruction(val type: Type, val a: String, val b: String?)

    data class AluState(var w: Int, var x: Int, var y: Int, var z: Int, var max: Long, var min: Long) {
        override fun equals(other: Any?): Boolean {
            if (other !is AluState) return false

            return w == other.w && x == other.x && y == other.y && z == other.z
        }

        override fun hashCode() = 31 * w + 31 * x + 31 * y + 31 * z
    }

    class Alu {
        private var states = listOf(AluState(w = 0, x = 0, y = 0, z = 0, max = 0, min = 0))

        fun executeProgram(instructions: List<Instruction>): List<AluState> {
            instructions.forEach { executeInstruction(it) }
            return states
        }

        private fun executeInstruction(instruction: Instruction) {
            if (instruction.type == Type.INP) {
                states = states.groupingBy { it }
                    .reduce { _, acc, element ->
                        if (element.max > acc.max) acc.max = element.max
                        if (element.min < acc.min) acc.min = element.min
                        acc
                    }.flatMap { (_, state) ->
                        (1..9).map { i ->
                            state.copy(max = state.max * 10 + i, min = state.min * 10 + i)
                                .apply { set(instruction.a, i) }
                        }
                    }
                println("States: ${states.size}")
                return
            }

            for (state in states) {
                with(state) {
                    when (instruction.type) {
                        Type.ADD -> add(instruction)
                        Type.MUL -> mul(instruction)
                        Type.DIV -> div(instruction)
                        Type.MOD -> mod(instruction)
                        Type.EQL -> eql(instruction)
                        else -> IllegalStateException("Invalid instruction: $instruction")
                    }
                }
            }
        }

        private fun AluState.add(instruction: Instruction) {
            set(instruction.a, getA(instruction) + getB(instruction))
        }

        private fun AluState.mul(instruction: Instruction) {
            set(instruction.a, getA(instruction) * getB(instruction))
        }

        private fun AluState.div(instruction: Instruction) {
            set(instruction.a, getA(instruction) / getB(instruction))
        }

        private fun AluState.mod(instruction: Instruction) {
            set(instruction.a, getA(instruction) % getB(instruction))
        }

        private fun AluState.eql(instruction: Instruction) {
            set(instruction.a, if (getA(instruction) == getB(instruction)) 1 else 0)
        }

        private fun AluState.set(variable: String, value: Int) {
            when (variable) {
                "w" -> w = value
                "x" -> x = value
                "y" -> y = value
                "z" -> z = value
            }
        }

        private fun AluState.getA(instruction: Instruction): Int {
            return when (instruction.a) {
                "w" -> w
                "x" -> x
                "y" -> y
                "z" -> z
                else -> throw IllegalArgumentException("Invalid argument: ${instruction.a}")
            }
        }

        private fun AluState.getB(instruction: Instruction): Int {
            return when (instruction.b) {
                "w" -> w
                "x" -> x
                "y" -> y
                "z" -> z
                else -> instruction.b!!.toInt()
            }
        }
    }
}