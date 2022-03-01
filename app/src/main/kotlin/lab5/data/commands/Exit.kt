package lab5.data.commands

import lab5.data.exceptions.RedundantArgsException
import lab5.data.utilities.LanguageManager
import kotlin.system.exitProcess

/**
 * Command does not close any of the instances. Shuts off the program.
 */
class Exit(
    private val language: LanguageManager
): AbstractCommand(language) {

    @Throws(RedundantArgsException::class)
    fun execute(arguments: ArrayList<String>): Boolean {
        if (arguments.isNotEmpty()) {
            throw RedundantArgsException(language)
        } else exitProcess(54)
    }

    fun safeExecute(arguments: ArrayList<String>): Boolean {
        try {
            execute(arguments)
        } catch (e: RedundantArgsException) {
            println(e.message)
            if (Proceed(language).safeExecute()) {
                exitProcess(54)
            } else return false
        }
        return true
    }
}