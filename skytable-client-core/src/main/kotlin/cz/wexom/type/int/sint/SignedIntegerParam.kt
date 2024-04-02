package cz.wexom.type.int.sint

import cz.wexom.type.int.IntegerParam
import cz.wexom.type.Param

open class SignedIntegerParam<T>(signedInt: T) : IntegerParam<T>(signedInt, Param.Type.SIGNED_INTEGER)