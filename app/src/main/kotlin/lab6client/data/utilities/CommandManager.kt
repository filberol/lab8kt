package lab6client.data.utilities

import lab6client.data.commands.*
import lab6client.data.commands.InteractiveAdd
import lab6client.run.Console
import lab6client.server.ConnectionHandler

/**
 * Data class storing commands.
 * Invoke commands by their name.
 * Commands can be executed locally or sent to the server by themselves.
 */
data class CommandManager(
    private val history: HistoryManager,
    private val lang: LanguageManager,
    private val collection: CollectionManager,
    private val config: ConfigManager,
    private val comparator: PersonComparator,
    private val validator: FieldValidator,
    private val builder: ObjectBuilder,
    private val console: Console,
    private val connection: ConnectionHandler
) {
    private var arguments: ArrayList<String> = ArrayList()

    private val commandSet: HashMap<String, () -> Any> = hashMapOf(
        "exit" to {Exit(lang, connection).execute(arguments)},
        "history" to {History(lang).execute(arguments, history)},
        "add" to { InteractiveAdd(lang, validator, builder, collection, connection).execute(arguments, 0)},
        "clear" to {ClearCollection(lang, collection, connection).execute(arguments)},
        "update" to {UpdateID(lang, validator, builder, collection, connection).execute(arguments)},
        "show" to {PrintCollection(lang, collection).execute(arguments, comparator.idComparator)},
        "remove_by_id" to {RemoveByID(lang, collection, connection).execute(arguments)},
        "execute_script" to {ExecuteScript(lang, console).execute(arguments)},
        "remove_first" to {RemoveFirst(lang, collection, connection).execute(arguments, comparator.idComparator)},
        "remove_lower" to {RemoveLower(lang, collection, connection).execute(arguments, comparator)},
        "print_ascending" to {PrintCollection(lang, collection).execute(arguments,comparator.reverseComparator)},
        "filter_greater_than_birthday" to {Filter(lang, collection).execute(arguments, comparator.reverseComparator)},
        "print_field_descending_location" to {PrintCollection(lang, collection).execute(arguments,comparator.locationComparator)},
        "help" to {Help(lang).execute(arguments)},
        "refresh" to {Refresh(lang, collection, connection).execute(arguments)},
        "demotivator" to {Demotivate(lang).execute(arguments)}
    )

    fun eatCommand(command: String, args: ArrayList<String>) {
        arguments = args
        if (commandSet[command] == null) {
            println(lang.getString("NoSuchCommand"))
        }
        if (commandSet[command] != null || config.getLogU()) {
            history.saveCommand(command)
        }
        commandSet[command]?.invoke()
    }
}