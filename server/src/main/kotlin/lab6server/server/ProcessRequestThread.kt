package lab6server.server

import lab6server.data.utilities.CollectionManager
import lab6server.data.utilities.LanguageManager
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import common.entities.Request
import common.entities.Answer
import java.io.EOFException
import java.net.SocketException

/**
 * Class providing mainly logic of processing requests and giving answer to client.
 * Receives already created streams.
 */
class ProcessRequestThread(
    private val collection: CollectionManager,
    private val language: LanguageManager,
    private val tokens: TokenManager,
    private val receiver: ObjectInputStream,
    private val sender: ObjectOutputStream,
    private val sqlHandler: SqlHandler
): Runnable {
    private var active = true

    override fun run() {
        while (active) {
        processRequest()
        }
    }

    private fun processRequest() {
        try {
            val req = receiver.readObject() as Request
            println(req)
            //Action logic

            //CheckUser
            if (sqlHandler.userManager.checkAddUser(req.getUser())) {
                //Add or delete element
                if (req.geElement() != null) {
                    //Add if id = 0
                    if (req.geElement()!!.getID() == 0) {
                        val element = req.geElement()
                        element!!.setID(collection.getFreeID())
                        collection.addNotNull(element)
                    //Delete if id !=0
                    } else {
                        val id = req.geElement()!!.getID()
                        collection.deleteByID(id)
                    }
                }
                //Common answer

                //Reaction depends on active token
                if (tokens.checkPopToken(req.getUser().getToken())) {
                    giveAnswer(req.getVer(), "Done")
                } else {
                    giveAnswer(req.getVer(), "SucAcc")
                }
            } else {
                //Create Account automatically
                giveAnswer(req.getVer(), "NewAcc")
            }
        } catch (e: ClassCastException) {
            println(language.getString("DataLoss"))
        } catch (e: EOFException) {
            println(language.getString("disconnected"))
            active = false
        } catch (e: SocketException) {
            println(language.getString("disconnected"))
            active = false
        }
    }

    private fun giveAnswer(collVer: Int, res: String) {
        collection.getDiff(collVer).map {
            Answer(it.second, tokens.generateAddToken(), collection.getSize(), it.first)
        }.forEach(sender::writeObject)
        sender.writeObject(res)

    }
}