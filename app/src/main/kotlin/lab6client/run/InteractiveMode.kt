package lab6client.run

import lab6client.data.annotations.UserEnter
import java.util.Scanner
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

/**
 * Class-reader. Read line while true, form to command execution request.
 */
class InteractiveMode(
    private val console: Console,
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
            exitProcess(0)
        } catch (_: IndexOutOfBoundsException) {}
        return true
    }
}