package lab5.data.commands

import lab5.data.annotations.ServerCommand
import lab5.data.exceptions.NotEnoughArgsException
import lab5.data.utilities.CollectionManager
import lab5.data.utilities.FieldValidator
import lab5.data.utilities.LanguageManager
import lab5.data.utilities.ObjectBuilder

/**
 * Command updates the element under the specified ID.
 * Actually is an add and delete combination, so updating empty id creates an element.
 */
@ServerCommand
class UpdateID(
    private val language: LanguageManager,
    private val validator: FieldValidator,
    private val builder: ObjectBuilder,
    private val collection: CollectionManager
): AbstractCommand(language) {

    fun execute(arguments: ArrayList<String>): Boolean {
        if (arguments[0] != "id") throw NotEnoughArgsException(language)
        val id = arguments[1].toInt()
        if (AddElement(language, validator, builder, collection).safeExecute(ArrayList(), id)) {
            arguments.removeAt(0)
            RemoveByID(language, collection).execute(arguments)
        } else {
            println("cannot add")
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