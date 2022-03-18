package lab5.data.commands

import lab5.data.utilities.LanguageManager
import kotlin.system.exitProcess

/**
 * Command does not close any of the instances. Shuts off the program.
 */
class Exit(
    private val language: LanguageManager
): AbstractCommand(language) {
    fun execute(arguments: ArrayList<String>) {
        if (arguments.isEmpty() || (arguments.isNotEmpty() && Proceed(language).execute())) {
            exitProcess(0)
        }
    }
}