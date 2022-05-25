package lab6client.run

import lab6client.data.utilities.LanguageManager
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Class-reader. Read through the file and transform it to "interactive-like" input.
 */
class ScriptMode(
    private val console: Console,
    private val language: LanguageManager
) {

    fun readFile(path: String): Boolean {
        val scriptFile = File(path)
        val inputStream: InputStream = FileInputStream(scriptFile)
        val input = InputStreamReader(inputStream)
        val line = StringBuilder()
        var code: Int
        while (input.read().also { code = it } != -1) {
            line.append(code.toChar())
        }
        input.close()
        val lines = line.toString().split("\n")
        if (!scriptSet.contains(path)) {
            scriptSet.add(path)
            for (commandLine in lines) {
                val commandFeed = ArrayList<String>(commandLine.split(" "))
                commandFeed.removeAll(listOf(""))
                if (commandFeed.size != 0) {
                    val command = commandFeed[0]
                    commandFeed.removeAt(0)
                    console.eatCommand(command, commandFeed)
                }
            }
            scriptSet.remove(path)
        } else {
            println(language.getString("LoopExecute"))
        }
        return true
    }
    companion object ExecutingScripts {
        private var scriptSet: HashSet<String> = HashSet()
    }
}