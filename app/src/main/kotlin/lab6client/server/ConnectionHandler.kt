package lab6client.server

import common.entities.Answer
import common.entities.Request
import common.entities.User
import lab6client.data.utilities.CollectionManager
import lab6client.data.utilities.ConfigManager
import lab6client.data.utilities.LanguageManager
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.InetSocketAddress
import java.nio.channels.SocketChannel
import kotlin.ClassCastException

/**
 * Class handling the connection to the server.
 * For client uses stays compact and supports only requests for changing the original collection or refreshing.
 * Gets as an answer the diff of the server collection and message.
 */
class ConnectionHandler(
    private val language: LanguageManager,
    private val user: User,
    private val collection: CollectionManager,
    private val waiter: Object,
    config: ConfigManager
): Runnable {
    private val host: String = config.getAddress()
    private val port: Int = config.getPort()
    private val reconnectionTimeout: Long = 3000
    private val maxReconnectionAttempts = 3
    private lateinit var socketChannel: SocketChannel
    private lateinit var serverSender: ObjectOutputStream
    private lateinit var serverReceiver: ObjectInputStream

    private var attempts = 0
    private var connect = false
    fun isConnected() = connect

    fun getUser() = user
    fun clearAttempts() {attempts = 0}

    /**
     * Pulling the one method of the same object to create concurrency.
     */
    override fun run() { tryToConnect() }

    /**
     * Sets the connection with server, providing timeout and first download.
     * Works in different Thread not in control of the class itself
     */
    private fun tryToConnect() {
        if (attempts > 0) {
            println(language.getString("Reconnecting"))
        }
        try {
            attempts++
            socketChannel = SocketChannel.open(InetSocketAddress(host, port))
            serverSender = ObjectOutputStream(socketChannel.socket().getOutputStream())
            serverReceiver = ObjectInputStream(socketChannel.socket().getInputStream())
            println(language.getString("ConnectionAccept"))
            connect = true
            createRequest(Request(user, null, 0))
            attempts = 0
        } catch (e: IllegalArgumentException) {
            println(language.getString("ConnectionFalse"))
        } catch (e: IOException) {
            println(language.getString("ConnectionFail"))
            if (attempts <= maxReconnectionAttempts) {
                println(language.getString("ReconnectIn"))
                Thread.sleep(reconnectionTimeout)
                tryToConnect()
            } else {
                println(language.getString("CannotConnect"))
                println(language.getString("Offline"))
                synchronized(waiter) {
                    waiter.notifyAll()
                }
            }
        }
    }

    /**
     * Sends single request and goto multiple answers from server.
     */
    fun createRequest(req: Request){
        try {
            attempts = 0
            serverSender.reset()
            serverSender.writeObject(req)
            processAnswer(collection)
        } catch (e: IOException) {
            println(language.getString("RequestError"))
        }
    }

    /**
     * Answer receive finished by string message.
     * Until that waits for answer.
     */
    private fun processAnswer(coll: CollectionManager) {
        try {
            val answer = serverReceiver.readObject()
            //Check thr answer validity
            if (answer is Answer) {
                //Check if usual answer
                if (answer.getElement() != null) {
                    //Add or delete element
                    if (answer.getElement()!!.getAction()) {
                        coll.addNotNull(answer.getElement())
                    } else {
                        coll.deleteByID(answer.getElement()!!.getID())
                    }
                    coll.setVersion(answer.getVersion())
                    user.setToken(answer.getToken())
                    processAnswer(coll)
                //Opening clear command for invalid token
                } else if (answer.getVersion() < 0) {
                    collection.clear()
                    processAnswer(coll)
                }
            //Closing string answer
            } else {
                println(language.getString(answer.toString()))
            }
            return
        } catch (e: ClassCastException) {
            println(language.getString("NoResponse"))
        } catch (e: IOException) {
            println(language.getString("RequestError"))
        }
    }

    fun close() {
        try {
            println(language.getString("Closing"))
            serverSender.close()
            serverReceiver.close()
            socketChannel.close()
        } catch (_: IOException) {
        } catch (_: UninitializedPropertyAccessException) {}
    }
}