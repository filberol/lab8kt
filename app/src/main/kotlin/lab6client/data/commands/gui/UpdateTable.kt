package lab6client.data.commands.gui

import lab6client.data.commands.AbstractCommand
import lab6client.data.commands.Proceed
import lab6client.data.utilities.LanguageManager
import lab6client.gui.HomeFrame

class UpdateTable(
    private val language: LanguageManager,
    private val frame: HomeFrame?
): AbstractCommand(language) {
    fun execute(arguments: ArrayList<String>) {
        if (arguments.isEmpty() || (arguments.isNotEmpty().also { println(language.getString("RedundantArgs")) }
                    && Proceed(language).execute())) {
            try {
                frame!!.updateTableTab()
            } catch (e: NullPointerException) {
                println(language.getString("CliMode"))
            }
        }
    }
}