package lab6client.data.commands

import lab6client.data.commands.server.ServerRemoveByID
import lab6client.data.utilities.CollectionManager
import lab6client.data.utilities.LanguageManager
import lab6client.server.ConnectionHandler

/**
 * Delete all the elements from the collection. Does nothing to collection file.
 */
class ClearCollection(
    private val language: LanguageManager,
    private val collection: CollectionManager,
    private val connection: ConnectionHandler
): AbstractCommand(language) {
    fun execute(arguments: ArrayList<String>) {
        if (arguments.isEmpty() || (arguments.isNotEmpty().also { println(language.getString("RedundantArgs")) }
                    && Proceed(language).execute())) {
            val idList= collection.getIds()
            for (id in idList) {
                ServerRemoveByID(language, collection, connection).execute(id)
            }
        }
    }
}