package lab5.data.commands

import lab5.data.exceptions.RedundantArgsException
import lab5.data.utilities.LanguageManager
import kotlin.system.exitProcess

class ExitCommand(
    private val language: LanguageManager
): Command(language) {

    @Throws(RedundantArgsException::class)
    override fun execute(arguments: ArrayList<String>): Boolean {
        if (arguments.isNotEmpty()) {
            throw RedundantArgsException(language)
        } else exitProcess(54)
    }

    override fun safeExecute(arguments: ArrayList<String>): Boolean {
        try {
            execute(arguments)
        } catch (e: RedundantArgsException) {
            println(e.message)
            if (ProceedCommand(language).safeExecute()) {
                exitProcess(54)
            } else return false
        }
        return true
    }
}