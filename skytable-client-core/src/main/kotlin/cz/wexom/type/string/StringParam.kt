package cz.wexom.type.string

import cz.wexom.type.Param
import java.nio.ByteBuffer

class StringParam(value: String) : Param<String>(value) {
    private var type: Type = Type.STRING

    override fun type() = type

    override fun encodedParam(): ByteArray {
        val size = size().toString().toByteArray()
        val buffer = ByteBuffer.allocate(1 + size.size + 1 + size() + 1)
        return buffer.put(type.value.toByte()).put(size).put('\n'.code.toByte()).put(value()).put('\n'.code.toByte())
            .array()
    }

    override fun value() = value.toByteArray()

    override fun size() = value.length

}