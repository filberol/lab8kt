package lab5.data.commands

import lab5.data.exceptions.NotEnoughArgsException
import lab5.data.utilities.CollectionManager
import lab5.data.utilities.FieldValidator
import lab5.data.utilities.LanguageManager
import lab5.data.utilities.ObjectBuilder

class UpdateIDCommand(
    private val language: LanguageManager,
    private val validator: FieldValidator,
    private val builder: ObjectBuilder,
    private val collection: CollectionManager
): Command(language) {

    override fun execute(arguments: ArrayList<String>): Boolean {
        if (arguments[0] != "id") throw NotEnoughArgsException(language)
        val id = arguments[1].toInt()
        if (AddElementCommand(language, validator, builder, collection).safeExecute(ArrayList(), id)) {
            arguments.removeAt(0)
            RemoveByIDCommand(language, collection).execute(arguments)
        } else {
            println("cannot add")
        }
        return true
    }

    override fun safeExecute(arguments: ArrayList<String>): Boolean {
        try {
            execute(arguments)
        } catch (e: Exception) {
            println(e.message)
            return false
        }
        return true
    }
}