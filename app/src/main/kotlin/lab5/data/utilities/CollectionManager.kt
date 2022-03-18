package lab5.data.utilities


import lab5.data.exceptions.FullCollectionException
import lab5.data.objects.Person
import java.time.LocalDate
import java.util.stream.Stream

/**
 * Class-handler. Stores the collection and provides basic commands for them.
 */
class CollectionManager(
    private val language: LanguageManager,
    ) {
    private var collection: ArrayDeque<Person> = ArrayDeque()
    private var ids: HashSet<Int> = HashSet()

    //Serialize - - - - - - - - - - - - - - - - - - - - - - -
    fun getSerializedStream(): Stream<List<String>> = collection.map {it.serialize()}.stream()

    //Add - - - - - - - - - - - - - - - - - - -
    fun addNotNull(element: Person?): Boolean {
        if (element != null) {
            ids.add(element.getID())
            collection.add(element)
            return true
        }
        return false
    }

    //Find - - - - - - - - - - - - - - - - - - - - -
    fun getSize() = collection.size

    fun getFreeID(): Int {
        for (id in 1..Int.MAX_VALUE) {
            if (!ids.contains(id)) {
                return id
            }
        }
        throw FullCollectionException(language)
    }

    fun getIndexBy(birthday: LocalDate): Int? {
        for (person in collection) {
            if (person.getBirthday() == birthday)
                return collection.indexOf(person)-1
        }
        return null
    }

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
            collection.slice(0..int).forEach(::println)
            println(language.getString("EndLine"))
            System.out.printf(language.getString("Total") + "\n", collection.size)
        } else {
            println(language.getString("EmptyCollection"))
        }
        return true
    }

    //Delete - - - - - - - - - - - - - - - - - -
    fun deleteByID(id: Int): Boolean {
        for(person in collection) {
            if (person.getID() == id) {
                collection.remove(person)
                return true
            }
        }
        return false
    }

    fun deleteByPosition(index: Int) = collection.removeAt(index)

    fun delete(int: Int): Boolean {
        for (i in 0..int) {
            deleteByPosition(0)
        }
        println(language.getString("Done"))
        return true
    }
}