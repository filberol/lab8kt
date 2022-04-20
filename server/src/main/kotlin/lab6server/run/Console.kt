package lab6server.run

import lab6server.data.utilities.*
import java.util.*
import kotlin.NoSuchElementException
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

/**
 * Console. Handles command instances and executes them with suitable arguments.
 * P.S. Здесь сыра нет!
 */
class Console(
    language: LanguageManager,
    collection: CollectionManager,
    fileManager: CollectionFileManager,
    config: ConfigManager,
    validator: FieldValidator,
    builder: ObjectBuilder,
    thread: RunPortThread
) {
    init {
        println(language.getString("Welcome") + "0.5.5 beta")
        println("SERVER       \\|||//")
        println("             (o.o)")
        println("=---------ooO-(_)-Ooo----------=")
    }

    private val manager: CommandManager = CommandManager(language, collection,
        fileManager, config, validator, builder, this, thread)

    fun eatCommand() {
        val scanner= Scanner(System.`in`)
        val lineFeed: String
        try {
            lineFeed = scanner.nextLine()
            val commandFeed = ArrayList<String>(lineFeed.split(" "))
            val command = commandFeed[0]
            commandFeed.removeAll(listOf(""))
            commandFeed.removeAt(0)
            manager.eatCommand(command, commandFeed)
        } catch (e: NoSuchElementException) {
            exitProcess(1)
        } catch (_: IndexOutOfBoundsException) {}
    }
}