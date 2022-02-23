package lab5.bin

import lab5.data.commands.*
import lab5.data.utilities.*

class Console(
    private  val history: HistoryManager,
    private val config: ConfigManager,
    private val lang: LanguageManager,
    private val collection: CollectionManager
) {
    init {
        println(lang.getString("Welcome") + "0.3.5 alpha")
        println("             \\|||/")
        println("             (o.o)")
        println("=---------ooO-(_)-Ooo----------=")
    }

    private val exit = ExitCommand(lang)
    private val hist = HistoryCommand(lang)

    fun eatCommand(command: String, arguments: ArrayList<String>) {
        when (command) {
            "exit" -> {
                history.saveCommand(command)
                exit.safeExecute(arguments)
            }
            "history" -> {
                hist.safeExecute(arguments, history)
                history.saveCommand(command)
            }
            else -> {
                history.saveCommand(command)
                println("$command "+lang.getString("NoSuchCommand"))
            }
        }
    }
}