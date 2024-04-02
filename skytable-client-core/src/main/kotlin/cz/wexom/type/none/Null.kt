package cz.wexom.type.none

import cz.wexom.type.Param
import java.nio.ByteBuffer

class Nothing
class Null : Param<Nothing>(Nothing()) {
    private var type: Type = Type.NULL

    override fun type() = type

    override fun encodedParam(): ByteArray {
        val buffer = ByteBuffer.allocate(size())
        return buffer.put(type.value.toByte()).put('\n'.code.toByte()).array()
    }

    override fun value() = byteArrayOf()

    override fun size() = 2
}