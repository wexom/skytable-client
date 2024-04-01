package cz.wexom.connection

class ConnectionBuilder {
    private var id: Int = 1
    private var host: String? = null
    private var port: Int = 2003
    private var username: String = "root"
    private var password: String? = null
    private var bufferSize: Int = 1024

    fun id(id: Int): ConnectionBuilder {
        this.id = id
        return this
    }

    fun host(host: String): ConnectionBuilder {
        this.host = host
        return this
    }

    fun port(port: Int): ConnectionBuilder {
        this.port = port
        return this
    }

    fun username(username: String): ConnectionBuilder {
        this.username = username
        return this
    }

    fun password(password: String): ConnectionBuilder {
        this.password = password
        return this
    }

    fun bufferSize(bufferSize: Int): ConnectionBuilder {
        this.bufferSize = bufferSize
        return this
    }

    fun build(): BlockingConnection {
        return BlockingConnection(id, host!!, port, username, password!!, bufferSize)
    }
}