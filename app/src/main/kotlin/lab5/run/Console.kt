package lab5.run

import lab5.data.utilities.*

/**
 * Console. Handles command instances and executes them with suitable arguments.
 * P.S. Здесь сыра нет!
 */
class Console(
    history: HistoryManager,
    language: LanguageManager,
    collection: CollectionManager,
    fileManager: CollectionFileManager,
    config: ConfigManager,
    comparator: PersonComparator,
    validator: FieldValidator,
    builder: ObjectBuilder,
) {
    init {
        println(language.getString("Welcome") + "0.5.3 alpha")
        println("             \\|||/")
        println("             (o.o)")
        println("=---------ooO-(_)-Ooo----------=")
    }

    private val manager: CommandManager = CommandManager(history, language, collection, fileManager,
        config, comparator, validator, builder, this)

    fun eatCommand(command: String, arguments: ArrayList<String>) {
        manager.eatCommand(command, arguments)
    }
}