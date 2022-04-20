package lab6client.data.commands.server

import lab6client.data.annotations.ServerCommand
import lab6client.data.commands.AbstractCommand
import lab6client.data.utilities.CollectionManager
import lab6client.data.utilities.LanguageManager
import lab6client.server.ConnectionHandler
import common.entities.Request

/**
 * Server command sending remove request with specific id element to the server.
 * Invokes processing the answer from the server.
 */
@ServerCommand
class ServerRemoveByID(
    private val language: LanguageManager,
    private val collection: CollectionManager,
    private val connection: ConnectionHandler
): AbstractCommand(language) {
    fun execute(id: Int) {
        try {
            val element = collection.getByID(id)!!
            element.setAction(false)
            connection.createRequest(Request(connection.getUser(), element, collection.getVersion()))
        } catch (e: NullPointerException) {
            println(language.getString("EmptySearch"))
        } catch (e: UninitializedPropertyAccessException) {
            println(language.getString("NotConnected"))
        }
    }
}