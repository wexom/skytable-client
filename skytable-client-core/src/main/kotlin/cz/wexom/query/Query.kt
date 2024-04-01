package cz.wexom.query

import cz.wexom.type.NullParam
import cz.wexom.type.Param
import cz.wexom.type.Signed64BitIntegerParam
import cz.wexom.type.StringParam
import cz.wexom.type.Unsigned64BitIntegerParam

class Query(private val query: String, vararg args: Any?) {
    private var params: List<Param<*>> = mutableListOf()

    init {
        if (args.isNotEmpty()) {
            initParams(args)
        }
    }

    private fun to64BitInteger(number: Long): Param<Long> {
        return if (number >= 0) {
            Unsigned64BitIntegerParam(number)
        } else {
            Signed64BitIntegerParam(number)
        }
    }

    private fun initParams(args: Array<out Any?>) {
        params = args.map {
            when (it) {
                null -> {
                    NullParam()
                }

                is Int -> {
                    to64BitInteger(it.toLong())
                }

                is Long -> {
                    to64BitInteger(it)

                }

                is String -> {
                    StringParam(it)
                }

                else -> throw IllegalArgumentException("Unsupported type")
            }
        }
    }

    fun query() = query

    fun params() = params
}