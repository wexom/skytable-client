package cz.wexom.authentication

import mu.KotlinLogging

val logger = KotlinLogging.logger {}

const val AUTHENTICATION_HEADER = 'H'.code.toByte()
const val AUTHENTICATION_RESPONSE_PACKET_SIZE = 4

fun authenticationPacket(username: String, password: String): ByteArray {
    return byteArrayOf(AUTHENTICATION_HEADER, 0x00, 0x00, 0x00, 0x00, 0x00) + "${username.length}\n${password.length}\n${username}${password}".toByteArray()
}

fun authenticationResult(response: ByteArray): Result<Unit> {
    return if (response.size != 4 || response[3] != 0.toByte()) {
        //TODO implement more detailed error handling
        logger.trace{"Authentication failed"}
        Result.failure(Exception("Authentication failed"))
    } else {
        logger.trace{"Authentication successful"}
        Result.success(Unit)
    }
}