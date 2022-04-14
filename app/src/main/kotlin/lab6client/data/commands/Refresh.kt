package lab6client.data.commands

import lab6client.data.commands.server.ServerAdd
import lab6client.data.utilities.CollectionManager
import lab6client.data.utilities.LanguageManager
import lab6client.server.ConnectionHandler

/**
 * Refresh collection from the server.
 * Does not decide whether to update or fully download the collection.
 */
class Refresh(
    private val language: LanguageManager,
    private val collection: CollectionManager,
    private val connection: ConnectionHandler
): AbstractCommand(language) {
    fun execute(arguments: ArrayList<String>) {
        if (arguments.isEmpty() || (arguments.isNotEmpty().also { println(language.getString("RedundantArgs")) }
                    && Proceed(language).execute())) {
            ServerAdd(language, collection, connection).execute(null)
        }
    }
}