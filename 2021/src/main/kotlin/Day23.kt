import java.util.*
import kotlin.math.abs

class Day23 {

    fun first(input: String): Int {
        val burrow = parseInput(input)

        return calculateCost(burrow)
    }

    fun second(input: String): Int {
        val burrow = parseInput(input, unfolded = true)

        return calculateCost(burrow)
    }

    private fun parseInput(input: String, unfolded: Boolean = false): Burrow {
        var lines = input.lines().drop(2)
        if (unfolded) {
            lines = lines.take(1) +
                listOf(
                    "  #D#C#B#A#",
                    "  #D#B#A#C#"
                ) +
                lines.drop(1)
        }
        val rooms = arrayOf(3, 5, 7, 9).mapIndexed { i, room ->
            val values = (0..if (unfolded) 3 else 1).map { lines[it][room] }
            Room(values, ROOM_TYPE[i])
        }

        return Burrow(Hallway(), rooms, energy = 0)
    }

    private fun calculateCost(start: Burrow): Int {
        var minCost = Int.MAX_VALUE

        val queue = PriorityQueue(listOf(start))
        val cache = mutableMapOf(start to 0)

        while (queue.isNotEmpty()) {
            val burrow = queue.poll()

            if (burrow.rooms.all { room -> room.isComplete() }) {
                if (minCost > (cache[burrow] ?: Int.MAX_VALUE)) {
                    minCost = cache.getValue(burrow)
                }
            }

            // Move to room
            burrow.hallway.hallway.forEachIndexed { hallwayIndex, amphipod ->
                if (amphipod != null) {
                    if (burrow.hallway.canMove(from = hallwayIndex, to = AMPHIPOD_ROOM_INDEX.getValue(amphipod)) &&
                        burrow.rooms[amphipodRoomOrder(amphipod)].canInsert(amphipod)
                    ) {
                        val nBurrow = burrow.moveToRoom(amphipodRoomOrder(amphipod), hallwayIndex)
                        if ((cache[nBurrow] ?: Int.MAX_VALUE) > nBurrow.energy) {
                            cache[nBurrow] = nBurrow.energy
                            queue.add(nBurrow)
                        }
                    }
                }
            }

            // Move to hallway
            burrow.rooms.forEachIndexed { roomIndex, room ->
                if (!room.isComplete()) {
                    room.values.forEachIndexed { valueIndex, amphipod ->
                        if (amphipod != null && room.canMove(from = valueIndex)) {
                            VALID_INDEX.forEach { hallwayIndex ->
                                if (burrow.hallway.canMove(from = ROOM_INDEX[roomIndex], to = hallwayIndex)) {
                                    val nBurrow = burrow.moveToHallway(roomIndex, hallwayIndex)
                                    if ((cache[nBurrow] ?: Int.MAX_VALUE) > nBurrow.energy) {
                                        cache[nBurrow] = nBurrow.energy
                                        queue.add(nBurrow)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return minCost
    }

    companion object {
        val AMPHIPOD_ROOM_INDEX = mapOf(
            'A' to 2,
            'B' to 4,
            'C' to 6,
            'D' to 8,
        )

        fun amphipodRoomOrder(amphipod: Char) = ROOM_TYPE.indexOf(amphipod)
        val AMPHIPOD_ENERGY = mapOf(
            'A' to 1,
            'B' to 10,
            'C' to 100,
            'D' to 1000,
        )
        val ROOM_TYPE = listOf('A', 'B', 'C', 'D')
        val ROOM_INDEX = arrayOf(2, 4, 6, 8)
        val VALID_INDEX = arrayOf(0, 1, 3, 5, 7, 9, 10)
    }

    data class Hallway(val hallway: List<Char?> = Array<Char?>(11) { null }.toList()) {
        fun canMove(from: Int, to: Int): Boolean {
            val range = if (from < to) (from + 1)..to else (from - 1) downTo to

            return range.all { hallway[it] == null }
        }

        override fun toString() = hallway.joinToString(separator = "") { it?.toString() ?: "." }
    }

    data class Room(val values: List<Char?>, val type: Char) {
        fun isComplete() = values.all { it == type }
        fun canInsert(value: Char) = value == type && values.all { it == null || it == type }

        fun pop(): Triple<Room, Char, Int> {
            val value = values.withIndex().first { it.value != null }
            val nValues = values.toMutableList().apply { set(value.index, null) }
            return Triple(copy(values = nValues), value.value!!, value.index + 1)
        }

        fun insert(value: Char): Pair<Room, Int> {
            val index = values.indexOfLast { it == null }
            val nValues = values.toMutableList().apply { set(index, value) }
            return copy(values = nValues) to index + 1
        }

        fun canMove(from: Int): Boolean {
            val upRange = (from - 1) downTo 0
            val downRange = from until values.size

            return upRange.all { values[it] == null } && !downRange.all { values[it] == type }
        }
    }

    data class Burrow(val hallway: Hallway, val rooms: List<Room>, val energy: Int) : Comparable<Burrow> {

        fun moveToHallway(roomIndex: Int, hallwayIndex: Int): Burrow {
            val (nRoom, amphipod, deep) = rooms[roomIndex].pop()
            val nRooms = rooms.toMutableList().apply { set(roomIndex, nRoom) }
            val nHallway = hallway.hallway.toMutableList().apply { set(hallwayIndex, amphipod) }
            val nEnergy =
                ((abs(ROOM_INDEX[roomIndex] - hallwayIndex) + deep) * AMPHIPOD_ENERGY.getValue(amphipod)) + energy

            return Burrow(hallway.copy(hallway = nHallway), nRooms, nEnergy)
        }

        fun moveToRoom(roomIndex: Int, hallwayIndex: Int): Burrow {
            val nHallway = hallway.hallway.toMutableList()
            val amphipod = nHallway.set(hallwayIndex, null)!!
            val (nRoom, deep) = rooms[roomIndex].insert(amphipod)
            val nRooms = rooms.toMutableList().apply { set(roomIndex, nRoom) }
            val nEnergy =
                ((abs(ROOM_INDEX[roomIndex] - hallwayIndex) + deep) * AMPHIPOD_ENERGY.getValue(amphipod)) + energy

            return Burrow(hallway.copy(hallway = nHallway), nRooms, nEnergy)
        }

        override fun compareTo(other: Burrow) = energy.compareTo(other.energy)

        override fun equals(other: Any?): Boolean {
            if (other !is Burrow) return false

            return hallway == other.hallway && rooms == other.rooms
        }

        override fun hashCode() = hallway.hashCode() + rooms.hashCode()
    }
}