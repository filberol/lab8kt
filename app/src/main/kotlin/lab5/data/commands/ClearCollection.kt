package lab5.data.commands

import lab5.data.exceptions.RedundantArgsException
import lab5.data.utilities.CollectionManager
import lab5.data.utilities.LanguageManager

/**
 * Delete all the elements from the collection. Does nothing to collection file.
 */
class ClearCollection(
    private val language: LanguageManager
): AbstractCommand(language) {

    @Throws(RedundantArgsException::class)
    private fun execute(arguments: ArrayList<String>, collection: CollectionManager): Boolean {
        if (arguments.isNotEmpty()) {
            throw RedundantArgsException(language)
        } else {
            if (Proceed(language).safeExecute()) {
                collection.delete(collection.getSize()-1)
            }
        }
        return true
    }

    fun safeExecute(arguments: ArrayList<String>, collection: CollectionManager): Boolean {
        try {
            execute(arguments, collection)
        } catch(e: RedundantArgsException) {
            if (Proceed(language).safeExecute()) {
                collection.delete(collection.getSize()-1)
            }
        }
        return true
    }
}