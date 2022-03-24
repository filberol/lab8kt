package lab6client.data.commands

import lab6client.data.utilities.HistoryManager
import lab6client.data.utilities.LanguageManager

/**
 * Prints executed commands and their execution time.
 * Number of stored commands is stored in the config file.
 */
class History(
    private val language: LanguageManager
): AbstractCommand(language) {
    fun execute(arguments: ArrayList<String>, history: HistoryManager) {
        if (arguments.isEmpty() || (arguments.isNotEmpty().also { println(language.getString("RedundantArgs")) }
                    && Proceed(language).execute())) {
            history.printHistory()
        }
    }
}