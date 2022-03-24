package lab6server.server

import lab6server.data.utilities.CollectionManager
import lab6server.data.utilities.LanguageManager
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import common.entities.Request
import common.entities.Answer
import java.io.EOFException

class ProcessRequestThread(
    private val collection: CollectionManager,
    private val language: LanguageManager,
    private val users: UserHandler,
    private val receiver: ObjectInputStream,
    private val sender: ObjectOutputStream
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
            if (req.geElement() == null) {
                giveAnswer(0)
            } else if (req.geElement()!!.getID() == 0) {
                val element = req.geElement()
                element!!.setID(collection.getFreeID())
                collection.addNotNull(element)
                giveAnswer(req.getVer())
            } else {
                val id = req.geElement()!!.getID()
                collection.deleteByID(id)
                giveAnswer(req.getVer())
            }
        } catch (e: ClassCastException) {
            println(language.getString("DataLoss"))
        } catch (e: EOFException) {
            println(language.getString("disconnected"))
            active = false
        }
    }

    private fun giveAnswer(collVer: Int) {
        collection.getDiff(collVer).map {
            Answer(it.second, users.generateToken(), collection.getSize(), it.first)
        }.forEach(sender::writeObject)
        sender.writeObject(null)

    }
}