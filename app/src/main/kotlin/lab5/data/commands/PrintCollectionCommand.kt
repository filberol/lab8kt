package lab5.data.commands

import lab5.data.exceptions.RedundantArgsException
import lab5.data.utilities.CollectionManager
import lab5.data.utilities.LanguageManager

class PrintCollectionCommand(
    private val language: LanguageManager,
    private val collection: CollectionManager
): AbstractCommand(language) {

    override fun execute(arguments: ArrayList<String>): Boolean {
        if (arguments.isNotEmpty()) {
            throw RedundantArgsException(language)
        } else {
            collection.printCollection()
        }
        return false
    }

    override fun safeExecute(arguments: ArrayList<String>): Boolean {
        try {
            execute(arguments)
        } catch (e: RedundantArgsException) {
            println(e.message)
            if (ProceedCommand(language).safeExecute()) {
                collection.printCollection()
            } else return false
        }
        return true
    }
}