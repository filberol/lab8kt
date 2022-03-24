package lab6client.data.commands

import common.objects.Person
import lab6client.data.utilities.CollectionManager
import lab6client.data.utilities.LanguageManager

/**
 * Command prints whole collection, sorting it by with the specified Comparator before.
 */
class PrintCollection(
    private val language: LanguageManager,
    private val collection: CollectionManager,
): AbstractCommand(language) {
    fun execute(arguments: ArrayList<String>, comparator: Comparator<Person>) {
        if (arguments.isEmpty() || (arguments.isNotEmpty().also { println(language.getString("RedundantArgs")) }
                    && Proceed(language).execute())) {
            collection.sortWith(comparator).printCollection(collection.getSize()-1)
        }
    }
}