package lab6client.data.commands

import lab6client.data.utilities.LanguageManager
import lab6client.server.ConnectionHandler
import kotlin.system.exitProcess

/**
 * Command does not close any of the instances. Shuts off the program.
 */
class Exit(
    private val language: LanguageManager,
    private val connection: ConnectionHandler
): AbstractCommand(language) {
    fun execute(arguments: ArrayList<String>) {
        if (arguments.isEmpty() || (arguments.isNotEmpty().also { println(language.getString("RedundantArgs")) }
                    && Proceed(language).execute())) {
            connection.close()
            exitProcess(0)
        }
    }
}