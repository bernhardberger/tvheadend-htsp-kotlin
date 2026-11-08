package at.bernhardberger.tvheadend.htsp

import at.bernhardberger.tvheadend.htsp.wire.*

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HtspGoldenCorpusTest {

    @Test
    fun scalarTypes_pinS64Utf8BinaryBooleanAndUuidBytes() {
        val frame = loadHtspGoldenFrame("scalar-types.hex")
        val decoded = HtspCodec.readMessage(ByteArrayInputStream(frame))

        assertEquals(0x93, frame.size - 4)
        assertEquals("types", decoded.method)
        assertEquals(0L, decoded.fields["zero"])
        assertEquals(0x1234L, decoded.fields["positive"])
        assertEquals(-2L, decoded.fields["negative"])
        assertEquals("Živě", decoded.fields["text"])
        assertEquals("", decoded.fields["empty"])
        assertArrayEquals(byteArrayOf(0, -1, 0x47), decoded.fields["binary"] as ByteArray)
        assertEquals(true, decoded.fields["enabled"])
        assertArrayEquals(ByteArray(16) { it.toByte() }, (decoded.fields["uuid"] as HtspWireUuid).bytes())
        assertArrayEquals(frame, encode(decoded.method!!, decoded.fields - "method"))
    }

    private fun encode(method: String, fields: Map<String, Any?>): ByteArray =
        ByteArrayOutputStream().also { it.write(HtspCodec.encode(method, fields)) }.toByteArray()
}
