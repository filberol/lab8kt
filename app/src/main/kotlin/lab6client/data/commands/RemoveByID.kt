package lab6client.data.commands

import lab6client.data.annotations.ServerCommand
import lab6client.data.commands.server.ServerRemoveByID
import lab6client.data.utilities.CollectionManager
import lab6client.data.utilities.LanguageManager
import lab6client.server.ConnectionHandler

/**
 * Removes collection element by its ID
 */
@ServerCommand
class RemoveByID(
    private val language: LanguageManager,
    private val collection: CollectionManager,
    private val connection: ConnectionHandler
): AbstractCommand(language) {
    fun execute(arguments: ArrayList<String>) {
        try {
            ServerRemoveByID(language, collection, connection).execute(arguments[0].toInt())
        } catch (e: NumberFormatException) {
            println(language.getString("NotEnoughArgs"))
        } catch (e: ArrayIndexOutOfBoundsException) {
            println(language.getString("NotEnoughArgs"))
        }
    }
}