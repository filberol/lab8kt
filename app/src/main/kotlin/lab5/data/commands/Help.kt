package lab5.data.commands

import lab5.data.utilities.LanguageManager

/**
 * Help command -_-
 */
class Help(
    private val language: LanguageManager
): AbstractCommand(language) {
    fun execute(arguments: ArrayList<String>) {
        if (arguments.isEmpty() || (arguments.isNotEmpty() && Proceed(language).execute())) {
            language.getString("CommandList").split("/").forEach(::println)
        }
    }
}