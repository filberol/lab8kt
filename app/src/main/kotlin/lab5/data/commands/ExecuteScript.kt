package lab5.data.commands

import lab5.run.Console
import lab5.run.ScriptMode
import lab5.data.utilities.LanguageManager
import java.io.FileNotFoundException
import java.io.IOException

/**
 * Commands in ScriptMode are the same as user input.
 * Executes not in the Silent mode.
 */
class ExecuteScript(
    private val language: LanguageManager,
    private val console: Console
): AbstractCommand(language) {
    /**
     * Creates a new instance of ScriptMode for each script file.
     * Uses existing instance of the Console.
     */
    fun execute(arguments: ArrayList<String>) {
        if (arguments.isNotEmpty()) {
            for (path in arguments) {
                try {
                    ScriptMode(console, language).readFile(path)
                } catch (e: FileNotFoundException) {
                    println(path + language.getString("DataFileNotFound"))
                } catch (e: SecurityException) {
                    println(path + language.getString("DataNoAccess"))
                } catch (e: IOException) {
                    println(path + language.getString("DataIOError"))
                }
            }
        } else println(language getString "NotEnoughArgs" )
    }
}