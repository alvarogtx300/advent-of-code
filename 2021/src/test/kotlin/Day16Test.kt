import kotlin.test.Test
import kotlin.test.assertEquals

class Day16Test {
    private val day16 = Day16()

    @Test
    fun first() {
        assertEquals(16, day16.first("8A004A801A8002F478"))
        assertEquals(12, day16.first("620080001611562C8802118E34"))
        assertEquals(23, day16.first("C0015000016115A2E0802F182340"))
        assertEquals(31, day16.first("A0016C880162017C3686B18A3D4780"))
    }

    @Test
    fun firstFile() {
        val result = day16.first(getResourceAsText("day16.input"))

        assertEquals(877, result)
    }

    @Test
    fun second() {
        assertEquals(3, day16.second("C200B40A82"))
        assertEquals(54, day16.second("04005AC33890"))
        assertEquals(7, day16.second("880086C3E88112"))
        assertEquals(9, day16.second("CE00C43D881120"))
        assertEquals(1, day16.second("D8005AC2A8F0"))
        assertEquals(0, day16.second("F600BC2D8F"))
        assertEquals(0, day16.second("9C005AC2F8F0"))
        assertEquals(1, day16.second("9C0141080250320F1802104A08"))
    }

    @Test
    fun secondFile() {
        val result = day16.second(getResourceAsText("day16.input"))

        assertEquals(194435634456, result)
    }

}