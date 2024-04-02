package cz.wexom.query

import cz.wexom.type.int.sint.ByteParam
import cz.wexom.type.int.sint.IntParam
import cz.wexom.type.int.sint.LongParam
import cz.wexom.type.none.Null
import cz.wexom.type.Param
import cz.wexom.type.int.sint.ShortParam
import cz.wexom.type.int.uint.UByteParam
import cz.wexom.type.int.uint.UIntParam
import cz.wexom.type.int.uint.ULongParam
import cz.wexom.type.int.uint.UShortParam
import cz.wexom.type.string.StringParam
import cz.wexom.type.int.uint.UnsignedIntegerParam

class Query(private val query: String, vararg args: Any?) {
    private var params: List<Param<*>> = mutableListOf()

    init {
        if (args.isNotEmpty()) {
            initParams(args)
        }
    }

    private fun initParams(args: Array<out Any?>) {
        params = args.map {
            when (it) {
                null -> Null()
                is Byte -> ByteParam(it)
                is Short -> ShortParam(it)
                is Int -> IntParam(it)
                is Long -> LongParam(it)
                is UByte -> UByteParam(it)
                is UShort -> UShortParam(it)
                is UInt -> UIntParam(it)
                is ULong -> ULongParam(it)
                is String -> StringParam(it)
                else -> throw IllegalArgumentException("Unsupported type")
            }
        }
    }

    fun query() = query

    fun params() = params
}