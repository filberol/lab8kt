package lab6client.run

import common.entities.User
import lab6client.data.annotations.UserEnter
import java.util.Scanner
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

/**
 * Class-reader. Read line while true, form to command execution request.
 */
class InteractiveMode(
    private val console: Console,
    private val user: User
) {
    private val scanner: Scanner = Scanner(System.`in`)
    @UserEnter
    fun commandLineRead() {
        try {
            print("${user.getLogin()}>")
            scanner.nextLine().split(";").forEach { commandRead(it.trim()) }
        } catch (e: NoSuchElementException) {
            exitProcess(0)
        }
    }

    private fun commandRead(lineFeed: String) {
        try {
            val commandFeed = ArrayList<String>(lineFeed.split(" "))
            val command = commandFeed[0].trim()
            commandFeed.removeAll(listOf(""))
            commandFeed.removeAt(0)
            console.eatCommand(command, commandFeed)
        } catch (_: IndexOutOfBoundsException) {}
    }
}