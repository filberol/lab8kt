package lab5.data.commands

import lab5.bin.Console
import lab5.bin.ScriptMode
import lab5.data.utilities.LanguageManager
import java.io.FileNotFoundException
import java.io.IOException

class ExecuteScriptCommand(
    private val language: LanguageManager,
    private val console: Console
): AbstractCommand(language) {
    fun execute(path: String): Boolean {
        ScriptMode(console).readFile(path)
        return true
    }

    fun safeExecute(arguments: ArrayList<String>): Boolean {
        if (arguments.isEmpty()) {
            for (path in arguments) {
                try {
                    execute(path)
                } catch (e: FileNotFoundException) {
                    println(path + language.getString("DataFileNotFound"))
                } catch (e: SecurityException) {
                    println(path + language.getString("DataNoAccess"))
                } catch (e: IOException) {
                    println(path + language.getString("DataIOError"))
                }
            }
        } else println(language.getString("TooManyArgsException"))
        return true
    }
}