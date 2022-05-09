package lab6client.data.commands

import lab6client.data.annotations.ServerCommand
import common.objects.Person
import lab6client.data.commands.server.ServerRemoveByID
import lab6client.data.utilities.CollectionManager
import lab6client.data.utilities.LanguageManager
import lab6client.server.ConnectionHandler

/**
 * Removes the first element in the collection with possibly no sorting.
 */
@ServerCommand
class RemoveFirst(
    private val language: LanguageManager,
    private val collection: CollectionManager,
    private val connection: ConnectionHandler
): AbstractCommand(language) {
    fun execute(arguments: ArrayList<String>, comparator: Comparator<Person>) {
        if (arguments.isEmpty() || (arguments.isNotEmpty().also { println(language.getString("RedundantArgs")) }
                    && Proceed(language).execute())) {
            collection.sortWith(comparator)
            ServerRemoveByID(language, collection, connection).execute(collection.removeByPosition(0).getID())
            println(language getString "Done")
        }
    }
}