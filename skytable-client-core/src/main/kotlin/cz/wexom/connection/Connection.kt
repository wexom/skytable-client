package cz.wexom.connection

import cz.wexom.query.Query

interface Connection {

    val id: Int
    val host: String
    val port: Int
    val username: String
    val password: String
    val bufferSize: Int

    fun isOpen(): Boolean

    fun isClosed(): Boolean

    fun close()

    fun open(): Result<Unit>

    fun query(query: Query): ByteArray
}