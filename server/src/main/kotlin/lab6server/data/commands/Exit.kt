package lab6server.data.commands

import lab6server.data.utilities.LanguageManager
import lab6server.run.RunServerThread
import kotlin.system.exitProcess

/**
 * Command does not close any of the instances. Shuts off the program.
 */
class Exit(
    private val language: LanguageManager,
    private val thread: RunServerThread
): AbstractCommand(language) {
    fun execute(arguments: ArrayList<String>) {
        if (arguments.isEmpty() || (arguments.isNotEmpty().also { println(language.getString("RedundantArgs")) }
                    && Proceed(language).execute())) {
            thread.close()
            exitProcess(0)
        }
    }
}