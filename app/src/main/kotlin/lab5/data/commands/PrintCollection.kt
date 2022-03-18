package lab5.data.commands

import lab5.data.objects.Person
import lab5.data.utilities.CollectionManager
import lab5.data.utilities.LanguageManager

/**
 * Command prints whole collection, sorting it by with the specified Comparator before.
 */
class PrintCollection(
    private val language: LanguageManager,
    private val collection: CollectionManager,
): AbstractCommand(language) {
    fun execute(arguments: ArrayList<String>, comparator: Comparator<Person>) {
        if (arguments.isEmpty() || (arguments.isNotEmpty() && Proceed(language).execute())) {
            collection.sortWith(comparator).printCollection(collection.getSize()-1)
        }
    }
}