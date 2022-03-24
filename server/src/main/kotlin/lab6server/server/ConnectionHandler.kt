package lab6server.server

import lab6server.data.utilities.CollectionManager
import lab6server.data.utilities.ConfigManager
import lab6server.data.utilities.LanguageManager
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException
import java.net.SocketTimeoutException

class ConnectionHandler(
    private val language: LanguageManager,
    private val collection: CollectionManager,
    private val users: UserHandler,
    config: ConfigManager
) {
    private val port: Int = config.getPort()
    private val soTimeout = config.getSoT()
    private lateinit var socket: ServerSocket
    private lateinit var sender: ObjectOutputStream
    private lateinit var receiver: ObjectInputStream

    fun openPort() {
        try {
            socket = ServerSocket(port)
            socket.soTimeout = soTimeout
        } catch (e: IOException) {
            println(language.getString("SocketError"))
        } catch (e: SecurityException) {
            println(language.getString("SocketError"))
        }  catch (e: IllegalArgumentException) {
            println(language.getString("ConnectionFalse"))
        }
    }

    fun waitForRequest() {
        try {
            println("Port $port")
            val clientSocket: Socket = socket.accept()
            receiver = ObjectInputStream(clientSocket.getInputStream())
            sender = ObjectOutputStream(clientSocket.getOutputStream())
            Thread(ProcessRequestThread(collection, language, users, receiver, sender)).start()
        } catch (e: IOException) {
            println(language.getString("SocketAccept"))
        } catch (e: SecurityException) {
            println(language.getString("SocketAccept"))
        }  catch (e: SocketTimeoutException) {
            println(language.getString("SoTimeout"))
        } catch (e: SocketException) {
            println(language.getString("Interrupt"))
        }
    }
}