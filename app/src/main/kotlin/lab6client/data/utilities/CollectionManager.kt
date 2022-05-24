package lab6client.data.utilities

import common.objects.Person
import java.time.LocalDate

/**
 * Class-handler. Stores the collection and provides basic commands for them.
 * On client stores the real collection in ArrayDeque.
 * Executes local commands e.g. sort, print locally.
 * Does not hold any external connections with file or web.
 * Provides only basic functions.
 */
class CollectionManager(
    private val language: LanguageManager,
    ) {
    private var collection: ArrayDeque<Person> = ArrayDeque()
    private var currentVersion: Int = 0
    private var ids = HashSet<Int>()


    //Server - - - - - - - - - - - - - - - - - - -
    fun getVersion() = currentVersion
    fun setVersion(ver: Int) {currentVersion = ver}

    //Add - - - - - - - - - - - - - - - - - - -
    fun addNotNull(element: Person?): Boolean {
        if (element != null) {
            collection.add(element)
            ids.add(element.getID())
            return true
        }
        return false
    }

    //Find - - - - - - - - - - - - - - - - - - - - -
    fun getSize() = collection.size

    fun getIds() = ids

    fun getByID(id : Int): Person? {
        for (person in collection) {
            if (person.getID() == id) return person
        }
        return null
    }

    fun findByPosition(index: Int): Int = collection[index].getID()

    fun getIndexBy(birthday: LocalDate): Int? {
        for (person in collection) {
            if (person.getBirthday() == birthday)
                return collection.indexOf(person)-1
        }
        return null
    }

    fun contains(id: Int): Boolean = ids.contains(id).also { println(ids) }

    //Sort, print - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    fun sortWith(comparator: Comparator<Person>): CollectionManager {
        collection.sortWith(comparator)
        return this
    }

    fun printCollection(int: Int): Boolean {
        if (collection.isNotEmpty()) {
            println(
                language.getString("TabHeading") + "\n" +
                        language.getString("TabHead") + "\n" +
                        language.getString("MidLine")
            )
            collection.slice(0..int).map { it.toTable() }.forEach(::println)
            println(language.getString("EndLine"))
            System.out.printf(language.getString("Total") + "\n", collection.size)
        } else {
            println(language.getString("EmptyCollection"))
        }
        return true
    }

    //Serialize - - - - - - - - - - - - - - - - - - - - - - -
    fun getTableData(): Array<Array<Any>> {
        return collection.map { element ->
            element.javaClass.declaredFields
                .onEach { field ->  field.isAccessible = true }
                .sortedBy { it.name }
                .filter { it.name != "action" }
                .map { field -> field.get(element) }
                .toTypedArray()
        }.toTypedArray()
    }

    //Delete - - - - - - - - - - - - - - - - - -
    fun deleteByID(id: Int): Boolean {
        for(person in collection) {
            if (person.getID() == id) {
                ids.remove(person.getID())
                collection.remove(person)
                return true
            }
        }
        return false
    }

    fun clear() {collection.clear()}
}