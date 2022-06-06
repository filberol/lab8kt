package lab6server.server

import lab6server.data.utilities.CollectionManager
import lab6server.data.utilities.LanguageManager
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import common.entities.Request
import common.entities.Answer
import lab6server.data.utilities.TokenManager
import org.postgresql.util.PSQLException
import java.io.EOFException
import java.net.SocketException

/**
 * Class providing mainly logic of processing requests and giving answer to client.
 * Receives already created streams.
 */
class ClientProcessorThread(
    private val collection: CollectionManager,
    private val language: LanguageManager,
    private val tokens: TokenManager,
    private val receiver: ObjectInputStream,
    private val sender: ObjectOutputStream
): Runnable {
    private var active = true
    private val dataBase = collection.getSql()

    override fun run() {
        while (active) {
        processRequest()
        }
    }

    private fun processRequest() {
        try {
            val req = receiver.readObject() as Request
            println(req)
            //Giving answer
            if (dataBase.userManager.checkUserExist(req.getUser())) {
                if (dataBase.userManager.checkUserPassword(req.getUser())) {
                    val contain = req.geElement()
                    if (contain != null) {
                        //Add if id = 0
                        synchronized(collection) {
                            if (contain.getID() == 0) {
                                contain.setID(collection.getFreeID())
                                collection.addToDb(contain)
                                //Delete if id !=0
                            } else {
                                collection.deleteByID(contain)
                            }
                        }
                    }
                    //Common answer

                    //Reaction depends on active token
                    if (tokens.checkPopToken(req.getUser().getToken())) {
                        giveAnswer(req.getVer(), "Done")
                    } else {
                        giveAnswer(0, "WrToken", true)
                    }
                } else {
                    giveDenial("UseExi")
                }
            } else {
                if (req.getRegister()) {
                    dataBase.userManager.tryAddUser(req.getUser())
                    println("added user")
                    giveAnswer(0, "SucReg", true)
                } else {
                    giveDenial("WrLogin")
                }
            }
        } catch (e: ClassCastException) {
            println(language.getString("DataLoss"))
        } catch (e: EOFException) {
            println(language.getString("disconnected"))
            active = false
        } catch (e: SocketException) {
            println(language.getString("disconnected"))
            active = false
        } catch (e: PSQLException) {
            giveDenial("SQlException")
        }
    }

    private fun giveAnswer(collVer: Int, res: String, truncate: Boolean = false) {
        if (truncate) sender.writeObject(Answer(null, tokens.generateAddToken(),
            -1))
        collection.getDiff(collVer).map {
            Answer(it, tokens.generateAddToken(), collection.getVersion())
        }.forEach(sender::writeObject)
        sender.writeObject(res)

    }

    private fun giveDenial(message: String) {
        sender.writeObject(message)
    }
}