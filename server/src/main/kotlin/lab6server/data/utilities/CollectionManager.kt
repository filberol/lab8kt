package lab6server.data.utilities


import lab6server.data.exceptions.FullCollectionException
import common.objects.Person
import java.util.stream.Stream

/**
 * Class-handler. Stores the collection and provides basic commands for them.
 */
//Автоматическое сохранение по таймеру или переполнению будет реализовано
//в последующих лабораторных
class CollectionManager(
    private val language: LanguageManager,
    ) {
    private var collection: ArrayList<Pair<Boolean, Person>> = ArrayList()
    private var ids: HashSet<Int> = HashSet()

    //Add - - - - - - - - - - - - - - - - - - -
    fun addNotNull(element: Person?): Boolean {
        if (element != null) {
            collection.add(Pair(true, element))
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

    //Print - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    fun printCollection(): Boolean {
        if (collection.isNotEmpty()) {
            println(
                language.getString("TabHeading") + "\n" +
                        language.getString("TabHead") + "\n" +
                        language.getString("MidLine")
            )
            collection.stream().map {it.second}.forEach(::println)
            println(language.getString("EndLine"))
            System.out.printf(language.getString("Total") + "\n", collection.size)
        } else {
            println(language.getString("EmptyCollection"))
        }
        return true
    }

    //Delete - - - - - - - - - - - - - - - - - -
    fun deleteByID(id: Int): Boolean {
        for(pair in collection) {
            if (pair.second.getID() == id) {
                collection.add(Pair(false, pair.second))
                return true
            }
        }
        return false
    }

    //Serialize - - - - - - - - - - - - - - - - - - - - - - -
    fun getSerializedStream(): Stream<List<String>> {
        val list = ArrayList<List<String>>()
        for (i in 0 until collection.size) {
            println(i)
            if (collection[i].first && !findRemoval(collection[i].second.getID())) {
                list.add(collection[i].second.serialize())
            }
        }
        return list.stream()
    }

    private fun findRemoval(id: Int): Boolean {
        for (el in collection) {
            if (!el.first && el.second.getID() == id) return true
            println("not found")
        }
        return false
    }

    //Stream Diff
    fun getDiff(ver: Int): Stream<Pair<Boolean, Person>> {
        return collection.stream().skip(ver.toLong())
    }
}