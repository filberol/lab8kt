package lab5.data.commands

import lab5.data.exceptions.RedundantArgsException
import lab5.data.utilities.HistoryManager
import lab5.data.utilities.LanguageManager

class HistoryCommand(
    private val language: LanguageManager
): Command(language) {

    @Throws(RedundantArgsException::class)
    private fun execute(arguments: ArrayList<String>
                        , history: HistoryManager): Boolean {
        if (arguments.isNotEmpty()) {
            throw RedundantArgsException(language)
        } else {
            history.printHistory()
        }
        return true
    }

    fun safeExecute(arguments: ArrayList<String>
                    , history: HistoryManager): Boolean {
        try {
            execute(arguments, history)
        } catch (e: RedundantArgsException) {
            println(e.message)
            if (ProceedCommand(language).safeExecute()) {
                history.printHistory()
            } else return false
        }
        return true
    }
}