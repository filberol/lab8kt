package lab6client.server

import common.entities.Answer
import common.entities.Request
import common.entities.User
import lab6client.data.commands.Proceed
import lab6client.data.utilities.CollectionManager
import lab6client.data.utilities.ConfigManager
import lab6client.data.utilities.LanguageManager
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.InetSocketAddress
import java.nio.channels.SocketChannel
import kotlin.ClassCastException
import kotlin.system.exitProcess

class ConnectionHandler(
    private val language: LanguageManager,
    private val user: User,
    private val collection: CollectionManager,
    config: ConfigManager
) {
    private val host: String = config.getAddress()
    private val port: Int = config.getPort()
    private val reconnectionTimeout: Long = 3000
    private val maxReconnectionAttempts = 3
    private lateinit var socketChannel: SocketChannel
    private lateinit var serverSender: ObjectOutputStream
    private lateinit var serverReceiver: ObjectInputStream


    private var attempts = 0

    fun getUser() = user

    fun tryToConnect(): Boolean {
        if (attempts > 0) {
            println(language.getString("Reconnecting"))
        }
        try {
            attempts++
            socketChannel = SocketChannel.open(InetSocketAddress(host, port))
            serverSender = ObjectOutputStream(socketChannel.socket().getOutputStream())
            serverReceiver = ObjectInputStream(socketChannel.socket().getInputStream())
            println(language.getString("ConnectionAccept"))
            createRequest(Request(user, null, 0))
            attempts = 0
            return true
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
                if (!Proceed(language).execute()) exitProcess(0)
            }
        }
        return false
    }

    fun createRequest(req: Request){
        try {
            attempts = 0
            serverSender.writeObject(req)
            processAnswer(collection)
        } catch (e: IOException) {
            println(language.getString("RequestError"))
        }
    }

    private fun processAnswer(coll: CollectionManager) {
        try {
            val answer = serverReceiver.readObject() as Answer?
            if (answer != null) {
                if (answer.action()) {
                    coll.addNotNull(answer.getElement())
                } else {
                    coll.deleteByID(answer.getElement().getID())
                }
                coll.setVersion(answer.getVersion())
                user.setToken(answer.getToken())
                processAnswer(coll)
            } else return
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
        } catch (e: IOException) {
            println(language.getString("CannotClose"))
        } catch (_: UninitializedPropertyAccessException) {}
    }
}