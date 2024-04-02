package cz.wexom.type

abstract class Param<T>(val value: T) {

    abstract fun type(): Type

    abstract fun encodedParam(): ByteArray

    internal abstract fun value(): ByteArray

    internal abstract fun size(): Int

    enum class Type(val value: Int) {
        NULL(0x00),
        BOOLEAN(0x01),
        UNSIGNED_INTEGER(0x02),
        SIGNED_INTEGER(0x03),
        FLOAT_64BIT(0x04),
        BINARY(0x05),
        STRING(0x06),
    }
}