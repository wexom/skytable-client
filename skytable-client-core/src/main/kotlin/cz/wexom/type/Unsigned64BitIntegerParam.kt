package cz.wexom.type

import java.nio.ByteBuffer

class Unsigned64BitIntegerParam(value: Long): Param<Long>(value) {
    private var type: Type = Type.UNSIGNED_64BIT_INTEGER

    override fun type() = type
    override fun encodedParam(): ByteArray {
        val buffer = ByteBuffer.allocate(1 + Long.SIZE_BYTES + 1)
        return buffer.put(type.value.toByte()).putLong(value()!!).put('\n'.code.toByte()).array()
    }
}