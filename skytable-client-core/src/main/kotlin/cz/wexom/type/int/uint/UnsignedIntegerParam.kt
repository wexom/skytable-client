package cz.wexom.type.int.uint

import cz.wexom.type.int.IntegerParam

open class UnsignedIntegerParam<T>(value: T) : IntegerParam<T>(value, Type.UNSIGNED_INTEGER)