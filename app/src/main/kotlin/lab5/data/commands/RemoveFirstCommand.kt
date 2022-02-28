package lab5.data.commands

import lab5.data.exceptions.RedundantArgsException
import lab5.data.objects.Person
import lab5.data.utilities.CollectionManager
import lab5.data.utilities.LanguageManager

class RemoveFirstCommand(
    private val language: LanguageManager,
    private val collection: CollectionManager
): AbstractCommand(language) {
    fun execute(arguments: ArrayList<String>, comparator: Comparator<Person>): Boolean {
        if (arguments.isNotEmpty()) {
            throw RedundantArgsException(language)
        } else {
            collection.sortWith(comparator)
            collection.deleteElement(0)
            println(language.getString("Done"))
            return true
        }
    }
    fun safeExecute(arguments: ArrayList<String>, comparator: Comparator<Person>): Boolean {
        try {
            execute(arguments, comparator)
        } catch (e: RedundantArgsException) {
            println(e.message)
            if (ProceedCommand(language).safeExecute()) {
                collection.sortWith(comparator)
                collection.deleteElement(0)
                println(language.getString("Done"))
            } else return false
        }
        return true
    }
}