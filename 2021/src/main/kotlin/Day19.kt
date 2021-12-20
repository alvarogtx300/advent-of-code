import kotlin.math.abs

class Day19 {

    fun first(input: String): Int {
        val fullMap = parseInput(input)

        return fullMap.findOverlappingBeacons().size
    }

    fun second(input: String): Int {
        val fullMap = parseInput(input)

        return fullMap.maxDistanceBetweenScanners()
    }

    private fun parseInput(input: String) = FullMap(buildList {
        val beacons = mutableListOf<Beacon>()
        input.lines().drop(1).filterNot(String::isEmpty).forEach {
            if (it.startsWith("---")) {
                add(Scanner(beacons.toList()))
                beacons.clear()
            } else {
                it.split(",")
                    .map(String::toInt)
                    .let { (x, y, z) -> beacons.add(Beacon(x, y, z)) }
            }
        }
        add(Scanner(beacons.toList()))
    })

    class FullMap(private val scanners: List<Scanner>) {
        private val referenceScanner = scanners.first()

        private val overlappingBeacons = mutableListOf<Beacon>().apply {
            val firstBeacon = referenceScanner.beacons.first()
            referenceScanner.move(-firstBeacon.x, -firstBeacon.y, -firstBeacon.z)
            addAll(referenceScanner.beacons)
        }
        private val simpleScanners =
            mutableListOf(SimpleScanner(referenceScanner.x, referenceScanner.y, referenceScanner.z))

        fun findOverlappingBeacons(): List<Beacon> {
            val remainingScanners = mutableListOf<Scanner>().apply {
                addAll(scanners.drop(1))
            }

            while (remainingScanners.isNotEmpty()) {
                val scanner = remainingScanners.removeFirst()
                var found = false

                if (checkOverlap(scanner, overlappingBeacons.toList())) {
                    found = true
                    overlappingBeacons.addAll(scanner.beacons.subtract(overlappingBeacons.toSet()))
                    simpleScanners.add(SimpleScanner(scanner.x, scanner.y, scanner.z))
                }

                if (!found) {
                    remainingScanners.add(scanner)
                }
            }

            return overlappingBeacons
        }

        fun maxDistanceBetweenScanners(): Int {
            findOverlappingBeacons()

            return simpleScanners.maxOf { a ->
                simpleScanners.maxOf { b ->
                    abs(a.x - b.x) + abs(a.y - b.y) + abs(a.z - b.z)
                }
            }
        }

        private fun checkOverlap(scanner: Scanner, beacons: List<Beacon>): Boolean {
            for (a in beacons) {
                // Move each beacon to origin
                val (x, y, z) = listOf(a.x, a.y, a.z)
                beacons.forEach { it.move(-x, -y, -z) }
                    .also { simpleScanners.forEach { it.move(-x, -y, -z) } }

                for (bRotation in scanner.rotationSequence()) {
                    for (b in bRotation.beacons) {
                        // Move b to origin to compare
                        val (xb, yb, zb) = listOf(b.x, b.y, b.z)
                        bRotation.move(-xb, -yb, -zb)

                        if (countOverlapping(bRotation.beacons, beacons) >= 12) {
                            return true
                        }
                    }
                }
            }
            return false
        }

        private fun countOverlapping(a: List<Beacon>, b: List<Beacon>) = a.intersect(b.toSet()).size
    }

    class SimpleScanner(
        var x: Int = 0,
        var y: Int = 0,
        var z: Int = 0
    ) {
        fun move(x: Int, y: Int, z: Int) {
            this.x += x
            this.y += y
            this.z += z
        }
    }

    class Scanner(val beacons: List<Beacon>) {
        var x: Int = 0
        var y: Int = 0
        var z: Int = 0

        fun move(x: Int, y: Int, z: Int) {
            this.x += x
            this.y += y
            this.z += z
            beacons.forEach { it.move(x, y, z) }
        }

        private fun roll() {
            y = -z.also { z = y }
            beacons.forEach(Beacon::roll)
        }

        private fun turnCw() {
            x = y.also { y = -x }
            beacons.forEach(Beacon::turnCw)
        }

        private fun turnCcw() {
            x = -y.also { y = x }
            beacons.forEach(Beacon::turnCcw)
        }

        fun rotationSequence() = sequence {
            for (rollIndex in (0..5)) {
                roll()
                yield(this@Scanner)
                for (turnIndex in (0..2)) {
                    when {
                        rollIndex % 2 == 0 -> turnCw()
                        else -> turnCcw()
                    }
                    yield(this@Scanner)
                }
            }
        }
    }

    data class Beacon(var x: Int, var y: Int, var z: Int) {

        fun move(x: Int, y: Int, z: Int) {
            this.x += x
            this.y += y
            this.z += z
        }

        fun roll() {
            y = -z.also { z = y }
        }

        fun turnCw() {
            x = y.also { y = -x }
        }

        fun turnCcw() {
            x = -y.also { y = x }
        }
    }
}
