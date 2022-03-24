package lab6server.data.commands

import lab6server.data.utilities.LanguageManager
import java.util.Scanner

/**
 * Command asks whether you want to proceed executing the command.
 */
class Proceed(
    private val language: LanguageManager,
    private val committer: Scanner = Scanner(System.`in`)
): AbstractCommand(language) {
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