package lab5.bin


import lab5.data.annotations.UserEnter
import lab5.data.utilities.LanguageManager
import java.util.Scanner
import kotlin.collections.ArrayList
import kotlin.system.exitProcess


class InteractiveMode(
    private val console: Console,
    private val lang: LanguageManager,
) {
    private val scanner: Scanner = Scanner(System.`in`)
    @UserEnter
    fun commandRead(): Boolean {
        val lineFeed: String
        try {
            lineFeed = scanner.nextLine()
            val commandFeed = ArrayList<String>(lineFeed.split(" "))
            val command = commandFeed[0]
            commandFeed.removeAll(listOf(""))
            commandFeed.removeAt(0)
            console.eatCommand(command, commandFeed)
        } catch (e: NoSuchElementException) {
            exitProcess(54)
        }
        return true
    }
}