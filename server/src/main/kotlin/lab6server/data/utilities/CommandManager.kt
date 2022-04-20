package lab6server.data.utilities

import lab6server.data.commands.TruncateUsers
import lab6server.data.commands.Exit
import lab6server.data.commands.PrintCollection
import lab6server.data.commands.SaveCollection
import lab6server.run.Console
import lab6server.run.RunPortThread

/**
 * Command manager on server is shortened, but the architecture stays.
 */
data class CommandManager(
    private val lang: LanguageManager,
    private val collection: CollectionManager,
    private val fileManager: CollectionFileManager,
    private val config: ConfigManager,
    private val validator: FieldValidator,
    private val builder: ObjectBuilder,
    private val console: Console,
    private val thread: RunPortThread
) {
    private var arguments: ArrayList<String> = ArrayList()

    private val commandSet: HashMap<String, () -> Any> = hashMapOf(
        "exit" to {Exit(lang, thread).execute(arguments)},
        "show" to { PrintCollection(lang, collection).execute(arguments)},
        "save" to { SaveCollection(lang, fileManager).execute(config.getDataPath())},
        "drop_users" to { TruncateUsers(lang, collection).execute(arguments) }
    )

    fun eatCommand(command: String, args: ArrayList<String>) {
        arguments = args
        if (commandSet[command] == null) {
            println(lang.getString("NoSuchCommand"))
        }
        commandSet[command]?.invoke()
    }
}