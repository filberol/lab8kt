package lab5.data.utilities

import lab5.data.commands.AddElementCommand
import lab5.data.exceptions.FullCollectionException
import lab5.data.objects.*
import lab5.data.parsers.CsvParser
import java.io.FileNotFoundException
import java.io.IOException

class CollectionManager(
    private val language: LanguageManager,
    private val validator: FieldValidator,
    private val builder: ObjectBuilder
    ) {
    private var collection: ArrayDeque<Person> = ArrayDeque()
    private var ids: HashSet<Int> = HashSet()

    fun safeLoad(path: String): Boolean {
        try {
            loadCollection(path)
        } catch (e: FileNotFoundException) {
            println(path + language.getString("DataFileNotFound"))
        } catch (e: SecurityException) {
            println(path + language.getString("DataNoAccess"))
        } catch (e: IOException) {
            println(path + language.getString("DataIOError"))
        }
        return false
    }

    @Throws(IOException::class)
    fun loadCollection(path: String): Boolean {
        println(language.getString("CollectionLoad"))

        val serializedCollection: ArrayList<Array<String>> = CsvParser().readCollection(path)
        serializedCollection.removeAt(0)

        for (element: Array<String> in serializedCollection) {
            AddElementCommand(language, validator, builder, this).execute(element)
        }
        System.out.printf(language.getString("LoadCount") + "\n", collection.size)
        return true
    }

    fun addNotNull(element: Person?): Boolean {
        if (element != null) {
            ids.add(element.getID())
            collection.add(element)
        }
        return true
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

    fun printCollection(): Boolean {
        if (collection.isNotEmpty()) {
            println(
                language.getString("TabHeading") + "\n" +
                        language.getString("TabHead") + "\n" +
                        language.getString("MidLine")
            )
            collection.forEach(::println)
            println(language.getString("EndLine"))
            System.out.printf(language.getString("Total") + "\n", collection.size)
        } else {
            println(language.getString("EmptyCollection"))
        }
        return true
    }

    fun delete(): Boolean {
        return true
    }
}