package lab5.data.commands

import lab5.data.annotations.UserEnter
import lab5.data.exceptions.SyntaxException
import lab5.data.utilities.LanguageManager
import java.util.Scanner

/**
 * Command asks whether you want to proceed executing the command.
 */
class Proceed(
    private val language: LanguageManager,
): AbstractCommand(language) {

    @UserEnter
    @Throws(SyntaxException::class)
    override fun execute(): Boolean {
        println(language.getString("Commitment"))
        val commit = Scanner(System.`in`)
        when (commit.nextLine()) {
            "Y" -> return true
            "N" -> return false
        }
        throw SyntaxException(language)
    }

    override fun safeExecute(): Boolean {
        return try {
            execute()
        } catch (e: SyntaxException) {
            println(e.message)
            safeExecute()
        }
    }
}