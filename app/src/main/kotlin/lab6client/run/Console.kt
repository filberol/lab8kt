package lab6client.run

import common.entities.User
import lab6client.data.utilities.*
import lab6client.gui.HomeFrame
import lab6client.server.ConnectionHandler

/**
 * Console. Handles command instances and executes them with suitable arguments.
 * P.S. Здесь сыра нет!
 */
class Console(
    history: HistoryManager,
    language: LanguageManager,
    collection: CollectionManager,
    config: ConfigManager,
    comparator: PersonComparator,
    validator: FieldValidator,
    builder: ObjectBuilder,
    connection: ConnectionHandler,
    gui: HomeFrame,
    user: User
) {
    init {
        println(language.getString("Welcome") + "0.5.5 beta")
        println("             \\|||/")
        println("             (o.o)")
        println("=---------ooO-(_)-Ooo----------=")
    }

    private val manager: CommandManager = CommandManager(history, language, collection,
        config, comparator, validator, builder, this, connection, gui, user)

    fun eatCommand(command: String, arguments: ArrayList<String>) {
        manager.eatCommand(command, arguments)
    }
}