class Day16 {

    fun first(input: String): Int {
        val packetParser = parseInput(input)
        val mainPacket = packetParser.parseMainPacket()

        return mainPacket.getVersions().sum()
    }

    fun second(input: String): Long {
        val packetParser = parseInput(input)
        val mainPacket = packetParser.parseMainPacket()

        return mainPacket.getValue()
    }

    private fun parseInput(input: String) = PacketParser(input.chunked(2)
        .map { it.toUByte(radix = 16) }
        .joinToString(separator = "") { it.toString(radix = 2).padStart(length = 8, padChar = '0') })

    class PacketParser(private val binaryString: String) {

        fun parseMainPacket(): Packet {
            val (header, body) = parseHeaderAndBody(binaryString)

            return when (header.type) {
                Packet.Type.LITERAL -> LiteralPacket(header, body)
                else -> parseOperatorPacket(header, body)
            }
        }

        private fun parseHeaderAndBody(rawString: String): Pair<Packet.Header, String> {
            val header = Packet.Header(rawString.substring(0..5))
            val body = rawString.substring(startIndex = 6)
            return Pair(header, body)
        }

        private fun parseOperatorPacket(header: Packet.Header, body: String): OperatorPacket {
            val lengthType = body[0].toString().toInt(radix = 2)

            return when (lengthType) {
                0 -> {
                    val length = body.substring(1..15).toInt(radix = 2)
                    val subPackets = parsePacketList(body.substring(16..15 + length))
                    val realBody = body.take(16 + length)
                    OperatorPacket(header, realBody, subPackets)
                }
                1 -> {
                    val packetLimit = body.substring(1..11).toInt(radix = 2)
                    val subPackets = parsePacketList(body.substring(startIndex = 12), packetLimit)
                    val realBody = body.take(12 + subPackets.sumOf(Packet::bitCount))
                    OperatorPacket(header, realBody, subPackets)
                }
                else -> error("Length Type ID invalid: $lengthType")
            }
        }

        private fun parsePacketList(rawString: String, packetLimit: Int = Int.MAX_VALUE): List<Packet> {
            var remainingString = rawString

            return buildList {
                while (remainingString.isNotEmpty() && size != packetLimit) {
                    val (header, body) = parseHeaderAndBody(remainingString)

                    val packet = when (header.type) {
                        Packet.Type.LITERAL -> LiteralPacket(header, parseLiteralBody(body))
                        else -> parseOperatorPacket(header, body)
                    }
                    add(packet)

                    remainingString = remainingString.drop(packet.bitCount)
                }
            }
        }

        private fun parseLiteralBody(rawString: String) = buildString {
            rawString.chunked(5).forEach { bits ->
                append(bits)
                if (bits.first() == '0') return@buildString
            }
        }

        abstract class Packet(val header: Header, body: String) {
            private val raw: String = header.raw + body
            val bitCount = raw.length

            abstract fun getValue(): Long
            abstract fun getVersions(): List<Int>

            class Header(val raw: String) {
                val version: Int = raw.substring(0..2).toInt(radix = 2)
                val type: Type = Type.fromInt(raw.substring(3..5).toInt(radix = 2))
            }

            enum class Type(private val value: Int) {
                SUM(0),
                PRODUCT(1),
                MIN(2),
                MAX(3),
                LITERAL(4),
                GREATER_THAN(5),
                LESS_THAN(6),
                EQUAL_TO(7);

                companion object {
                    private val map = values().associateBy(Type::value)
                    fun fromInt(value: Int) = map[value] ?: error("Invalid type: $value")
                }
            }
        }

        class LiteralPacket(header: Header, body: String) : Packet(header, body) {
            private val binaryValue = buildString {
                body.chunked(5).forEach { bits ->
                    append(bits.drop(1))
                    if (bits.first() == '0') return@buildString
                }
            }

            override fun getValue() = binaryValue.toLong(radix = 2)
            override fun getVersions(): List<Int> = listOf(header.version)
        }

        class OperatorPacket(header: Header, body: String, private val subPackets: List<Packet>) :
            Packet(header, body) {

            override fun getValue() = with(subPackets) {
                when (header.type) {
                    Type.SUM -> sumOf(Packet::getValue)
                    Type.PRODUCT -> map(Packet::getValue).reduce(Long::times)
                    Type.MIN -> minOf(Packet::getValue)
                    Type.MAX -> maxOf(Packet::getValue)
                    Type.GREATER_THAN -> map(Packet::getValue).let { (a, b) -> if (a > b) 1 else 0 }
                    Type.LESS_THAN -> map(Packet::getValue).let { (a, b) -> if (a < b) 1 else 0 }
                    Type.EQUAL_TO -> map(Packet::getValue).let { (a, b) -> if (a == b) 1 else 0 }
                    Type.LITERAL -> error("Invalid type: ${header.type}")
                }
            }

            override fun getVersions(): List<Int> = subPackets.flatMap(Packet::getVersions) + header.version
        }
    }
}
