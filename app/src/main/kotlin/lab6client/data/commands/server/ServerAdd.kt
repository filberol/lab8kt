package lab6client.data.commands.server

import lab6client.data.annotations.ServerCommand
import lab6client.data.commands.AbstractCommand
import lab6client.data.utilities.LanguageManager
import lab6client.data.utilities.CollectionManager
import lab6client.server.ConnectionHandler
import common.entities.Request
import common.objects.Person

/**
 * Server command sending add request with null id element to the server.
 * Invokes processing the answer from the server.
 */
@ServerCommand
class ServerAdd(
    private val language: LanguageManager,
    private val collection: CollectionManager,
    private val connection: ConnectionHandler
): AbstractCommand(language) {

    fun execute(element: Person?){
        try {
            connection.createRequest(Request(connection.getUser(), element, collection.getVersion()))
        } catch (e: UninitializedPropertyAccessException) {
            println(language.getString("NotConnected"))
        }
    }
}