package lab5.data.commands

import lab5.data.annotations.UserEnter
import lab5.data.utilities.LanguageManager
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
        when (committer.nextLine()) {
            "Y" -> return true
            "N" -> return false
            else -> execute()
        }
        return false
    }
}