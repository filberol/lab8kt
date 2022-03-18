package lab5.data.commands

import lab5.data.utilities.CollectionManager
import lab5.data.utilities.LanguageManager

/**
 * Delete all the elements from the collection. Does nothing to collection file.
 */
class ClearCollection(
    private val language: LanguageManager
): AbstractCommand(language) {
    fun execute(arguments: ArrayList<String>, collection: CollectionManager): Boolean {
        if (arguments.isEmpty() || (arguments.isNotEmpty() && Proceed(language).execute())) {
            return collection.delete(collection.getSize() - 1)
        }
        return false
    }
}