package lab5.data.commands

import lab5.data.objects.Person
import lab5.data.utilities.CollectionManager
import lab5.data.utilities.LanguageManager
import java.lang.NullPointerException
import java.time.DateTimeException
import java.time.LocalDate

/**
 * Command filters collection by specified comparator and value.
 */
class Filter(
    private val language: LanguageManager,
    private val collection: CollectionManager
): AbstractCommand(language) {

    fun execute(arguments: ArrayList<String>, comparator: Comparator<Person>) {
        if (arguments.isNotEmpty()) {
            try {
                collection.sortWith(comparator)
                collection.printCollection(collection.getIndexBy(LocalDate.parse(arguments[0]))!!)
            } catch (e: NullPointerException) {
                println(language getString "EmptySearch")
            } catch (e: DateTimeException) {
                println(language getString "NotEnoughArgs")
            }

        }
    }
}