package lab5.data.commands

import lab5.data.annotations.ServerCommand
import lab5.data.exceptions.NotEnoughArgsException
import lab5.data.utilities.CollectionManager
import lab5.data.utilities.LanguageManager
import lab5.data.utilities.PersonComparator
import java.lang.NullPointerException
import java.time.DateTimeException
import java.time.LocalDate

/**
 * Removes elements in collection, that does meet the specified criteria.
 */
@ServerCommand
class RemoveLower(
    private val language: LanguageManager,
    private val collection: CollectionManager,
): AbstractCommand(language) {
    fun execute(arguments: ArrayList<String>, comparator: PersonComparator): Boolean {
        if (arguments.isNotEmpty()) {
            collection.sortWith(comparator.birthdayComparator)
            collection.delete(collection.getIndexBy(LocalDate.parse(arguments[0]))!!)
        } else {
            throw NotEnoughArgsException(language)
        }
        return true
    }

    fun safeExecute(arguments: ArrayList<String>, comparator: PersonComparator): Boolean {
        try {
            execute(arguments, comparator)
        } catch (e: NotEnoughArgsException) {
            println(e.message)
        } catch (e: NullPointerException) {
            println(language.getString("EmptySearch"))
        } catch (e: DateTimeException) {
            println(language.getString("NotEnoughArgsException"))
        }
        return true
    }
}