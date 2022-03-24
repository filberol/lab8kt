package lab6client.data.commands

import lab6client.data.annotations.ServerCommand
import lab6client.data.commands.server.ServerRemoveByID
import lab6client.data.utilities.CollectionManager
import lab6client.data.utilities.LanguageManager
import lab6client.data.utilities.PersonComparator
import lab6client.server.ConnectionHandler
import java.lang.NullPointerException
import java.time.DateTimeException

/**
 * Removes elements in collection, that does meet the specified criteria.
 */
@ServerCommand
class RemoveLower(
    private val language: LanguageManager,
    private val collection: CollectionManager,
    private val connection: ConnectionHandler
): AbstractCommand(language) {
    fun execute(arguments: ArrayList<String>, comparator: PersonComparator){
        try {
            if (arguments.isNotEmpty()) {
                collection.sortWith(comparator.birthdayComparator)
                ServerRemoveByID(language, collection, connection).execute(collection.getByPosition(0).getID())
            } else {
                println(language getString "NotEnoughArgs")
            }
        } catch (e: NullPointerException) {
            println(language getString "EmptySearch")
        } catch (e: DateTimeException) {
            println(language getString "NotEnoughArgs")
        }
    }
}