package lab5.data.utilities

import lab5.data.commands.*
import lab5.run.Console

data class CommandManager(
    private val history: HistoryManager,
    private val lang: LanguageManager,
    private val collection: CollectionManager,
    private val fileManager: CollectionFileManager,
    private val config: ConfigManager,
    private val comparator: PersonComparator,
    private val validator: FieldValidator,
    private val builder: ObjectBuilder,
    private val console: Console,
) {
    private var arguments: ArrayList<String> = ArrayList()

    private val commandSet: HashMap<String, () -> Any> = hashMapOf(
        "exit" to {Exit(lang).execute(arguments)},
        "history" to {History(lang).execute(arguments, history)},
        "add" to {AddElement(lang, validator, builder, collection).interactiveAdd(arguments, 0)},
        "clear" to {ClearCollection(lang).execute(arguments, collection)},
        "update" to {UpdateID(lang, validator, builder, collection).execute(arguments)},
        "show" to {PrintCollection(lang, collection).execute(arguments, comparator.idComparator)},
        "remove_by_id" to {RemoveByID(lang, collection).execute(arguments)},
        "save" to {SaveCollection(lang, fileManager).execute(config.getDataPath())},
        "execute_script" to {ExecuteScript(lang, console).execute(arguments)},
        "remove_first" to {RemoveFirst(lang, collection).execute(arguments, comparator.idComparator)},
        "remove_lower" to {RemoveLower(lang, collection).execute(arguments, comparator)},
        "print_ascending" to {PrintCollection(lang, collection).execute(arguments,comparator.reverseComparator)},
        "filter_greater_than_birthday" to {Filter(lang, collection).execute(arguments, comparator.reverseComparator)},
        "print_field_descending_location" to {PrintCollection(lang, collection).execute(arguments,comparator.locationComparator)},
        "help" to {Help(lang).execute(arguments)}
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