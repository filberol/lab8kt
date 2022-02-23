package lab5.data.commands

import lab5.data.exceptions.SyntaxException
import lab5.data.utilities.LanguageManager
import java.util.Scanner
import kotlin.collections.ArrayList

class ProceedCommand(
    private val language: LanguageManager,
): Command(language) {

    @Throws(SyntaxException::class)
    private fun execute(): Boolean {
        println(language.getString("Commitment"))
        val commit = Scanner(System.`in`)
        when (commit.nextLine()) {
            "Y" -> return true
            "N" -> return false
        }
        throw SyntaxException(language)
    }

    fun safeExecute(): Boolean {
        return try {
            execute()
        } catch (e: SyntaxException) {
            println(e.message)
            safeExecute()
        }
    }
}