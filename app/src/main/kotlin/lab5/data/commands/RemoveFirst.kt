package lab5.data.commands

import lab5.data.annotations.ServerCommand
import lab5.data.objects.Person
import lab5.data.utilities.CollectionManager
import lab5.data.utilities.LanguageManager

/**
 * Removes the first element in the collection with possibly no sorting.
 */
@ServerCommand
class RemoveFirst(
    private val language: LanguageManager,
    private val collection: CollectionManager
): AbstractCommand(language) {
    fun execute(arguments: ArrayList<String>, comparator: Comparator<Person>) {
        if (arguments.isEmpty() || (arguments.isNotEmpty() && Proceed(language).execute())) {
            collection.sortWith(comparator)
            collection.deleteByPosition(0)
            println(language getString "Done")
        }
    }
}