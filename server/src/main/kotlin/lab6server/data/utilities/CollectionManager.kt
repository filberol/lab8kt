package lab6server.data.utilities


import lab6server.data.exceptions.FullCollectionException
import common.objects.Person
import lab6server.server.SqlHandler
import java.util.stream.Stream

/**
 * For server manger would only support add removal and streaming to console or file manager.
 */
//Автоматическое сохранение по таймеру или переполнению будет реализовано
//в последующих лабораторных
class CollectionManager(
    private val language: LanguageManager,
    private val sql: SqlHandler
    ) {
    private var collection: ArrayList<Pair<Boolean, Person>> = ArrayList()
    private var ids: HashSet<Int> = HashSet()

    //Add - - - - - - - - - - - - - - - - - - -
    fun addNotNull(element: Person?): Boolean {
        if (element != null) {
            //sql.
            collection.add(Pair(true, element))
            ids.add(element.getID())
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
            collection.stream().map {it.second.toTable()}.forEach(::println)
            println(language.getString("EndLine"))
            System.out.printf(language.getString("Total") + "\n", collection.size)
        } else {
            println(language.getString("EmptyCollection"))
        }
        return true
    }

    //Delete - - - - - - - - - - - - - - - - - -
    fun deleteByID(id: Int): Boolean {
        if (ids.contains(id)) {
            for (pair in collection) {
                if (pair.second.getID() == id) {
                    collection.add(Pair(false, pair.second))
                    ids.remove(id)
                    return true
                }
            }
        }
        return false
    }

    fun fullClearDiff() {
        collection.clear()
    }

    //Serialize - - - - - - - - - - - - - - - - - - - - - - -
    fun getSerializedStream(): Stream<List<String>> {
        val list = ArrayList<List<String>>()
        for (i in 0 until collection.size) {
            if (collection[i].first && !popRemoval(collection[i].second.getID())) {
                list.add(collection[i].second.serialize())
            }
        }
        return list.stream()
    }

    private fun popRemoval(id: Int): Boolean {
        for (el in collection) {
            if (!el.first && el.second.getID() == id) return true
            collection.remove(el)
        }
        return false
    }

    //Stream Diff
    fun getDiff(ver: Int): Stream<Pair<Boolean, Person>> {
        return collection.stream().skip(ver.toLong())
    }
}