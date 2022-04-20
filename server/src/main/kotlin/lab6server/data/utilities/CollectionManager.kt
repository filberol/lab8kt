package lab6server.data.utilities


import lab6server.data.exceptions.FullCollectionException
import common.objects.Person
import lab6server.server.SqlHandler
import java.util.stream.Stream

/**
 * For server manger would only support add removal and streaming to console or file manager.
 */
class CollectionManager(
    private val language: LanguageManager,
    config: ConfigManager
    ) {
    private val sql = SqlHandler(language, config, this)
    private var ids: HashSet<Int> = HashSet()
    private var collVer: Int = 0

    //Add - - - - - - - - - - - - - - - - - - -
    fun addToDb(element: Person?): Boolean {
        if (element != null) {
            ids.add(element.getID())
            collVer += 1
            return sql.dataManager.addToDb(element)
        }
        return false
    }

    //Find - - - - - - - - - - - - - - - - - - - - -
    fun getVersion() = collVer
    fun getSql() = sql

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
        if (ids.isNotEmpty()) {
            println(
                language.getString("TabHeading") + "\n" +
                        language.getString("TabHead") + "\n" +
                        language.getString("MidLine")
            )
            sql.dataManager.streamDiff(0).forEach { println(it.toTable()) }
            println(language.getString("EndLine"))
            System.out.printf(language.getString("Total") + "\n", ids.size)
        } else {
            println(language.getString("EmptyCollection"))
        }
        return true
    }

    //Delete - - - - - - - - - - - - - - - - - -
    fun deleteByID(element: Person): Boolean {
        if (ids.contains(element.getID())) {
            addToDb(element)
            ids.remove(element.getID())
        }
        return false
    }

    fun fullClearDiff() {
        collVer = 0
        sql.dataManager.truncateData()
    }

    //Serialize - - - - - - - - - - - - - - - - - - - - - - -
    fun getSerializedStream(): Stream<List<String>> {
        val list = ArrayList<List<String>>()
        for (id in ids) {
            list.add(sql.dataManager.findLastById(id).serialize())
        }
        return list.stream()
    }

    //Stream Diff
    fun getDiff(ver: Int): Stream<Person> {
        return sql.dataManager.streamDiff(ver)
    }

    fun getDbHandler() = sql
}