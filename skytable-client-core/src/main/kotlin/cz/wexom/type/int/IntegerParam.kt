package cz.wexom.type.int

import cz.wexom.type.Param
import java.nio.ByteBuffer

open class IntegerParam<T>(value: T, val type: Type) : Param<T>(value) {
    override fun type() = type

    override fun encodedParam(): ByteArray {
        val buffer = ByteBuffer.allocate(1 + size() + 1)
        return buffer.put(type.value.toByte()).put(value()).put('\n'.code.toByte()).array()
    }

    override fun value(): ByteArray = value.toString().toByteArray()

    override fun size(): Int = value.toString().length
}