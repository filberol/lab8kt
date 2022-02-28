package lab5.bin

import lab5.data.annotations.FileReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.InputStreamReader

class ScriptMode(
    private val console: Console
) {
    @FileReader
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
        for (commandLine in lines) {
            val commandFeed = ArrayList<String>(commandLine.split(" "))
            val command = commandFeed[0]
            commandFeed.removeAll(listOf(""))
            commandFeed.removeAt(0)
            console.eatCommand(command, commandFeed)
        }
        return true
    }
}