package lab6client.data.commands

import lab6client.data.annotations.UserEnter
import lab6client.data.utilities.LanguageManager
import java.util.Scanner

/**
 * Command asks whether you want to proceed executing the command.
 */
class Proceed(
    private val language: LanguageManager,
    private val committer: Scanner = Scanner(System.`in`)
): AbstractCommand(language) {
    @UserEnter
    override fun execute(): Boolean {
        print(language.getString("Commitment"))
        return when (committer.nextLine()) {
            "Y" -> true
            "N" -> false
            else -> {
                println(language.getString("SyntaxException"))
                execute()
            }
        }
    }
}