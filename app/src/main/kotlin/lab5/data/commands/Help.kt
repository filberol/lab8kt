package lab5.data.commands

import lab5.data.utilities.LanguageManager

/**
 * Help command -_-
 */
class Help(
    private val language: LanguageManager
): AbstractCommand(language) {
    override fun safeExecute(): Boolean{
        language.getString("CommandList").split("/").forEach(::println)
        return true
    }
}