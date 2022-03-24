package lab6client.data.commands

import lab6client.data.annotations.ServerCommand
import lab6client.data.commands.server.ServerAdd
import lab6client.data.utilities.*
import lab6client.server.ConnectionHandler
import kotlin.collections.ArrayList

/**
 * Big class with HardCoded elements.
 * Read input and add to collection or imitate it, adding from file.
 */
@ServerCommand
class InteractiveAdd(
    private val language: LanguageManager,
    private val validator: FieldValidator,
    private val builder: ObjectBuilder,
    private val collection: CollectionManager,
    private val connection: ConnectionHandler
): AbstractCommand(language) {

    fun execute(arguments: ArrayList<String>, id: Int): Boolean {
        if (arguments.isEmpty() || (arguments.isNotEmpty() && Proceed(language).execute())) {
            val element = FieldReader(language, validator, builder).askFields(id)!!
            ServerAdd(language, collection, connection).execute(element)
        }
        return true
    }
}