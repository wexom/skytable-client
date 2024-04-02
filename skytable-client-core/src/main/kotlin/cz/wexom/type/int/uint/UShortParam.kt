package cz.wexom.type.int.uint

class UShortParam(uShort: UShort) : UnsignedIntegerParam<UShort>(uShort) {
    override fun value(): ByteArray {
        println()
        return super.value()
    }
}