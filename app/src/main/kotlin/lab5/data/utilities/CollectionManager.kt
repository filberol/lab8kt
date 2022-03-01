package lab5.data.utilities


import lab5.data.exceptions.FullCollectionException
import lab5.data.objects.Person
import java.time.LocalDate

/**
 * Class-handler. Stores the collection and provides basic commands for them.
 */
class CollectionManager(
    private val language: LanguageManager,
    ) {
    private var collection: ArrayDeque<Person> = ArrayDeque()
    private var ids: HashSet<Int> = HashSet()

    fun serialize(): ArrayList<String> {
        val serialized: ArrayList<String> = ArrayList()
        collection.mapTo(serialized) {it.serialize()}
        return serialized
    }

    fun addNotNull(element: Person?): Boolean {
        if (element != null) {
            ids.add(element.getID())
            collection.add(element)
        }
        return true
    }

    fun getSize(): Int {
        return collection.size
    }

    fun getFreeID(): Int {
        for (id in 1..Int.MAX_VALUE) {
            if (!ids.contains(id)) {
                return id
            }
        }
        throw FullCollectionException(language)
    }

    fun deleteByID(id: Int): Boolean {
        for(person in collection) {
            if (person.getID() == id) {
                collection.remove(person)
                return true
            }
        }
        return false
    }

    fun getIndexBy(birthday: LocalDate): Int? {
        for (person in collection) {
            if (person.getBirthday() == birthday)
                return collection.indexOf(person)-1
        }
        return null
    }

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
            collection.slice(0..int).forEach(::println)
            println(language.getString("EndLine"))
            System.out.printf(language.getString("Total") + "\n", collection.size)
        } else {
            println(language.getString("EmptyCollection"))
        }
        return true
    }

    fun deleteElement(index: Int): Boolean {
        collection.removeAt(index)
        return true
    }

    fun delete(int: Int): Boolean {
        for (i in 0..int) {
            deleteElement(0)
        }
        println(language.getString("Done"))
        return true
    }
}