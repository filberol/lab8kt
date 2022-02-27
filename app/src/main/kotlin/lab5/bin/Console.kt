package lab5.bin

import lab5.data.commands.*
import lab5.data.utilities.*

class Console(
    private val history: HistoryManager,
    private val lang: LanguageManager,
    private val collection: CollectionManager,
    config: ConfigManager,
    validator: FieldValidator,
    builder: ObjectBuilder
) {
    init {
        println(lang.getString("Welcome") + "0.3.5 alpha")
        println("             \\|||/")
        println("             (o.o)")
        println("=---------ooO-(_)-Ooo----------=")
    }
    private val logUnknown: Boolean = config.getString("HistoryLogUnknown").toBoolean()

    private val exitCommand = ExitCommand(lang)
    private val historyCommand = HistoryCommand(lang)
    private val addElementCommand = AddElementCommand(lang, validator, builder, collection)
    private val clearCollectionCommand = ClearCollectionCommand(lang)
    private val updateIDCommand = UpdateIDCommand(lang, validator, builder, collection)
    private val printCollectionCommand = PrintCollectionCommand(lang, collection)
    private val removeByIDCommand = RemoveByIDCommand(lang, collection)

    fun eatCommand(command: String, arguments: ArrayList<String>) {
        when (command) {
            "exit" -> {
                history.saveCommand(command)
                exitCommand.safeExecute(arguments)
            }
            "history" -> {
                historyCommand.safeExecute(arguments, history)
                history.saveCommand(command)
            }
            "add" -> {
                history.saveCommand(command)
                addElementCommand.safeExecute(arguments, 0)
            }
            "clear" -> {
                history.saveCommand(command)
                clearCollectionCommand.safeExecute(arguments, collection)
            }
            "update" -> {
                history.saveCommand(command)
                updateIDCommand.safeExecute(arguments)
            }
            "show" -> {
                history.saveCommand(command)
                printCollectionCommand.safeExecute(arguments)
            }
            "remove_by_id" -> {
                history.saveCommand(command)
                removeByIDCommand.safeExecute(arguments)
            }
            else -> {
                if (logUnknown) {
                    history.saveCommand(command)
                }
                println("$command "+lang.getString("NoSuchCommand"))
            }
        }
    }
}