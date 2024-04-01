package cz.wexom.type

abstract class Param<T>(private var value: T? = null) {

    abstract fun type(): Type

    abstract fun encodedParam(): ByteArray

    enum class Type(val value: Int) {
        NULL(0x00),
        BOOLEAN(0x01),
        UNSIGNED_64BIT_INTEGER(0x02),
        SIGNED_64BIT_INTEGER(0x03),
        FLOAT_64BIT(0x04),
        BINARY(0x05),
        STRING(0x06),
    }

    open fun value(): T? = value
}