package cz.wexom.type

class NullParam: Param<Nothing>() {
    private var type: Type = Type.NULL

    override fun type() = type
    override fun encodedParam(): ByteArray = byteArrayOf(type.value.toByte())
}