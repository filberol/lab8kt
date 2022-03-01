package lab5.data.commands

import lab5.data.exceptions.RedundantArgsException
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

    fun execute(arguments: ArrayList<String>, comparator: Comparator<Person>): Boolean {
        if (arguments.isNotEmpty()) {
            throw RedundantArgsException(language)
        } else {
            collection.sortWith(comparator).printCollection(collection.getSize()-1)
        }
        return false
    }

    fun safeExecute(arguments: ArrayList<String>, comparator: Comparator<Person>): Boolean {
        try {
            execute(arguments, comparator)
        } catch (e: RedundantArgsException) {
            println(e.message)
            if (Proceed(language).safeExecute()) {
                collection.sortWith(comparator).printCollection(collection.getSize()-1)
            } else return false
        }
        return true
    }
}