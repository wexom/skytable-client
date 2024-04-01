package cz.wexom.connection

import cz.wexom.authentication.AUTHENTICATION_RESPONSE_PACKET_SIZE
import cz.wexom.authentication.authenticationPacket
import cz.wexom.authentication.authenticationResult
import cz.wexom.query.Query
import mu.KotlinLogging
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

class BlockingConnection(
    override val id: Int,
    override val host: String,
    override val port: Int,
    override val username: String,
    override val password: String,
    override val bufferSize: Int,
) : Connection {
    private var logger = KotlinLogging.logger {}
    private var socket: Socket? = null
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null

    init {
        initConnection()
    }

    private fun initConnection() {
        logger.trace {"Connecting to Skytable Server"}
        socket = Socket(host, port)
        if (socket!!.isConnected) {
            inputStream = socket!!.getInputStream()
            outputStream = socket!!.getOutputStream()
            logger.trace{"Connected to Skytable Server, Authenticating..."}
            authenticate()
        } else {
            logger.trace{"Failed to connect to Skytable Server"}
        }
    }

    private fun authenticate() {
        outputStream.run {
            this!!.write(authenticationPacket(username, password))
            this.flush()
            authenticationResult(readBytes(AUTHENTICATION_RESPONSE_PACKET_SIZE))
        }
    }

    private fun readBytes(): ByteArray = readBytes(bufferSize)

    private fun readBytes(bufferSize: Int): ByteArray {
        val buffer = ByteArray(bufferSize)
        val output = ByteArrayOutputStream()

        var bytesRead: Int
        do {
            bytesRead = inputStream!!.read(buffer)
            if (bytesRead > 0) {
                output.write(buffer, 0, bytesRead)
            }
        } while (bytesRead == bufferSize && inputStream!!.available() > 0)

        return output.toByteArray()
    }



    override fun isOpen() = socket?.isConnected ?: false

    override fun isClosed(): Boolean = socket?.isClosed ?: true

    override fun query(query: Query): ByteArray {
        val qWindow = query.query().length.toString()
        val header = byteArrayOf('S'.code.toByte())

        if (query.params().isNotEmpty()) {
            val parameters = query.params()
                .map { it.encodedParam() }
                .reduce { acc, param ->
                    acc + param
                }

            val packetSize = qWindow.length + 1 + query.query().length + parameters.size
            outputStream!!.write(
                header
                        + packetSize.toString().toByteArray()
                        + '\n'.code.toByte()
                        + qWindow.toByteArray()
                        + '\n'.code.toByte()
                        + query.query().toByteArray()
                        + parameters
            )
        } else {
            val packetSize = qWindow.length + 1 + query.query().length
            outputStream!!.write(
                header
                        + packetSize.toString().toByteArray()
                        + '\n'.code.toByte()
                        + qWindow.toByteArray()
                        + '\n'.code.toByte()
                        + query.query().toByteArray()
            )
        }

        outputStream!!.flush()
        return readBytes()
    }

    @Deprecated("Old query method")
    fun oldQuery(query: String, vararg args: String): ByteArray {
        val qWindow = query.length.toString()
        val header = byteArrayOf('S'.code.toByte())

        if (args.isNotEmpty()) {
            val param = byteArrayOf(0x06) + args[0].length.toString()
                .toByteArray() + '\n'.code.toByte() + args[0].toByteArray() + '\n'.code.toByte()
            val packetSize = qWindow.length + 1 + query.length + param.size
            outputStream!!.write(
                header + packetSize.toString()
                    .toByteArray() + '\n'.code.toByte() + qWindow.toByteArray() + '\n'.code.toByte() + query.toByteArray() + param
            )
        } else {
            val packetSize = qWindow.length + 1 + query.length
            outputStream!!.write(
                header + packetSize.toString()
                    .toByteArray() + '\n'.code.toByte() + qWindow.toByteArray() + '\n'.code.toByte() + query.toByteArray()
            )
        }
        outputStream!!.flush()
        return readBytes()
    }

    override fun close() {
        socket?.close()
        logger.trace{"Connection closed. Id: $id"}
    }

    override fun open(): Result<Unit> {
        return runCatching { initConnection() }
    }
}