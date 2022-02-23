package lab5.data.commands

import lab5.data.exceptions.*
import lab5.data.utilities.LanguageManager

/**
 * Abstract command when executing anything will return a boolean value as a feedback of the execution of the command.
 */
abstract class Command(lang: LanguageManager) {
    /**
     * Specified langHolder returns language needed to print to console.
     */
    private var language: LanguageManager

    /**
     * Some commands not needing arguments will throw exception if try to use them with.
     * You will be asked to proceed or not the following command.
     */
    init {
        language = lang
    }

    /**
     * Command providing additional info about a command.
     * Also try using 'help help'.
     */
    open fun printHelp(): Boolean {
        return true
    }

    /**
     * Unprotected execute command throwing pile of Exceptions to your face.
     * @throws ExecuteException Major error in executing the command.
     * @throws SyntaxException Just in case you don't understand what's written.
     * @throws RedundantArgsException Just in case you don't understand what's needed.
     */
    @Throws(ExecuteException::class, SyntaxException::class, RedundantArgsException::class)
    open fun execute(arguments: ArrayList<String>): Boolean {
        return true
    }

    /**
     * Protected execute command recursively calling usual execution and watching after exceptions.
     */
    open fun safeExecute(arguments: ArrayList<String>): Boolean {
        return true
    }

    /**
     * The command is identified as its class only. If the command already exists,
     * it will not be created again.
     */
    override fun hashCode(): Int {
        return this.javaClass.hashCode()
    }
}