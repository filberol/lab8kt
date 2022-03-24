package lab6client.data.commands

import lab6client.data.annotations.ServerCommand
import lab6client.data.utilities.CollectionManager
import lab6client.data.utilities.FieldValidator
import lab6client.data.utilities.LanguageManager
import lab6client.data.utilities.ObjectBuilder
import lab6client.server.ConnectionHandler

/**
 * Command updates the element under the specified ID.
 * Actually is an add and delete combination, so updating empty id creates an element.
 */
@ServerCommand
class UpdateID(
    private val language: LanguageManager,
    private val validator: FieldValidator,
    private val builder: ObjectBuilder,
    private val collection: CollectionManager,
    private val connection: ConnectionHandler
): AbstractCommand(language) {
    /**
     * Due to the add and removal methods, removing the same ID after adding one causes it to update.
     */
    fun execute(arguments: ArrayList<String>){
        if (arguments[0] == "id") {
            val id = arguments[1].toInt()
            if (InteractiveAdd(language, validator, builder, collection, connection).execute(ArrayList(), id)) {
                arguments.removeAt(0)
                RemoveByID(language, collection, connection).execute(arguments)
            } else {
                println(language getString "CannotUpdate")
            }
        } else {
            println(language getString "NotEnoughArgs")
        }
    }
}