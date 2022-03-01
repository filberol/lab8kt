package lab5.data.commands

import lab5.data.annotations.ServerCommand
import lab5.data.exceptions.NotEnoughArgsException
import lab5.data.utilities.CollectionManager
import lab5.data.utilities.LanguageManager

/**
 * Removes collection element by its ID
 */
@ServerCommand
class RemoveByID(
    private val language: LanguageManager,
    private val collection: CollectionManager
): AbstractCommand(language) {

    fun execute(arguments: ArrayList<String>): Boolean {
        if (arguments.isEmpty()) {
            throw NotEnoughArgsException(language)
        } else {
            collection.deleteByID(arguments[0].toInt())
            println(language.getString("Done"))
        }
        return true
    }

    fun safeExecute(arguments: ArrayList<String>): Boolean {
        try {
            execute(arguments)
        } catch (e: Exception) {
            println(e.message)
            return false
        }
        return true
    }
}