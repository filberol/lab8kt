package lab6client.data.commands

import lab6client.data.utilities.LanguageManager

/**
 * Help command -_-
 */
class Help(
    private val language: LanguageManager
): AbstractCommand(language) {
    fun execute(arguments: ArrayList<String>) {
        if (arguments.isEmpty() || (arguments.isNotEmpty().also { println(language.getString("RedundantArgs")) }
                    && Proceed(language).execute())) {
            language.getString("CommandList").split("/").forEach(::println)
        }
    }
}