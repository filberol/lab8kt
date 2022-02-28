package lab5.data.commands

import lab5.data.exceptions.RedundantArgsException
import lab5.data.utilities.CollectionManager
import lab5.data.utilities.LanguageManager


class ClearCollectionCommand(
    private val language: LanguageManager
): AbstractCommand(language) {

    @Throws(RedundantArgsException::class)
    private fun execute(arguments: ArrayList<String>, collection: CollectionManager): Boolean {
        if (arguments.isNotEmpty()) {
            throw RedundantArgsException(language)
        } else {
            if (ProceedCommand(language).safeExecute()) {
                collection.delete()
            }
        }
        return true
    }

    fun safeExecute(arguments: ArrayList<String>, collection: CollectionManager): Boolean {
        try {
            execute(arguments, collection)
        } catch(e: RedundantArgsException) {
            if (ProceedCommand(language).safeExecute()) {
                collection.delete()
            }
        }
        return true
    }
}