import java.lang.IllegalStateException
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day22 {

    fun first(input: String): Int {
        val instructions = parseInput(input)
        val reactor = Reactor()

        instructions.filterNot { it.isAboveLimits() }
            .forEach { reactor.executeInstruction(it) }

        return reactor.size.toInt()
    }

    fun second(input: String): Long {
        val instructions = parseInput(input)
        val reactor = Reactor()

        instructions.forEach { reactor.executeInstruction(it) }

        return reactor.size
    }

    private fun parseInput(input: String): List<Instruction> = input.lines()
        .map { it.split(" ") }
        .map { (state, rest) ->
            val (x, y, z) = rest.split(",").map {
                it.drop(2).let { axis ->
                    val start = axis.takeWhile { c -> c != '.' }.toInt()
                    val end = axis.takeLastWhile { c -> c != '.' }.toInt()
                    IntRange(start, end)
                }
            }
            Instruction(state == "on", Cube(x.first, x.last, y.first, y.last, z.first, z.last))
        }


    class Instruction(val state: Boolean, val cube: Cube) {
        fun isAboveLimits(): Boolean = with(cube) {
            x1 < -50 || x2 > 50 ||
            y1 < -50 || y2 > 50 ||
            z1 < -50 || z2 > 50
        }
    }

    data class Cube(val x1: Int, val x2: Int, val y1: Int, val y2: Int, val z1: Int, val z2: Int) {
        val size get() = (abs(x1 - x2) + 1L) * (abs(y1 - y2) + 1L) * (abs(z1 - z2) + 1L)

        fun contains(other: Cube): Boolean = x1 <= other.x1 && x2 >= other.x2 &&
            y1 <= other.y1 && y2 >= other.y2 &&
            z1 <= other.z1 && z2 >= other.z2

        fun intersect(other: Cube): Boolean = x2 >= other.x1 && x1 <= other.x2 &&
            y2 >= other.y1 && y1 <= other.y2 &&
            z2 >= other.z1 && z1 <= other.z2
    }

    class Reactor {
        private var cubes = setOf<Cube>()
        val size get() = cubes.sumOf(Cube::size)

        fun executeInstruction(instruction: Instruction) {
            when (instruction.state) {
                true -> turnOn(instruction)
                false -> turnOff(instruction)
            }
        }

        private fun turnOn(instruction: Instruction) {
            val instructionCube = instruction.cube

            val notIntersecting = cubes.filterNot { it.intersect(instructionCube) }.toSet()
            val intersecting = cubes.filter { it.intersect(instructionCube) }
                .filterNot { instructionCube.contains(it) }
            val sum = intersecting.fold(setOf(instructionCube)) { acc, cube ->
                turnOn(cube, acc)
            }

            cubes = notIntersecting + intersecting + sum
        }

        private fun turnOn(c1: Cube, ins: Set<Cube>): Set<Cube> {
            val notIntersecting = ins.filterNot { it.intersect(c1) }.toSet()
            val sum = ins.filter { it.intersect(c1) }
                .flatMap { sumCubes(c1, it) }
                .toSet()

            return notIntersecting + sum
        }

        private fun turnOff(instruction: Instruction) {
            cubes = cubes.flatMap { subtractCubes(it, instruction.cube) }.toSet()
        }

        private fun sumCubes(c1: Cube, c2: Cube) = when {
            c1.contains(c2) -> listOf(c1)
            c2.contains(c1) -> listOf(c2)
            c1.intersect(c2) -> subtractCubesIntersect(c2, c1)
            else -> throw IllegalStateException("Cubes don't intersect")
        }

        private fun subtractCubes(c1: Cube, c2: Cube) = when {
            c2.contains(c1) -> emptyList()
            c1.intersect(c2) -> subtractCubesIntersect(c1, c2)
            else -> listOf(c1)
        }

        private fun subtractCubesIntersect(c1: Cube, c2: Cube) = buildList {
            if (c1.x1 < c2.x1) { // -x
                add(c1.copy(x2 = c2.x1 - 1))
            }
            if (c1.x2 > c2.x2) { // +x
                add(c1.copy(x1 = c2.x2 + 1))
            }
            if (c1.y1 < c2.y1) { // -y
                add(c1.copy(y2 = c2.y1 - 1, x1 = max(c1.x1, c2.x1), x2 = min(c1.x2, c2.x2)))
            }
            if (c1.y2 > c2.y2) { // +y
                add(c1.copy(y1 = c2.y2 + 1, x1 = max(c1.x1, c2.x1), x2 = min(c1.x2, c2.x2)))
            }
            if (c1.z1 < c2.z1) { // -z
                add(
                    Cube(
                        x1 = max(c1.x1, c2.x1),
                        x2 = min(c1.x2, c2.x2),
                        y1 = max(c1.y1, c2.y1),
                        y2 = min(c1.y2, c2.y2),
                        z1 = c1.z1,
                        z2 = c2.z1 - 1
                    )
                )
            }
            if (c1.z2 > c2.z2) { // +z
                add(
                    Cube(
                        x1 = max(c1.x1, c2.x1),
                        x2 = min(c1.x2, c2.x2),
                        y1 = max(c1.y1, c2.y1),
                        y2 = min(c1.y2, c2.y2),
                        z1 = c2.z2 + 1,
                        z2 = c1.z2
                    )
                )
            }
        }
    }
}
