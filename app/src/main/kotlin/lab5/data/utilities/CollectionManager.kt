package lab5.data.utilities

import lab5.data.commands.AddElementCommand
import lab5.data.objects.*
import lab5.data.parsers.CsvParser
import java.io.FileNotFoundException
import java.io.IOException

class CollectionManager(
    private val language: LanguageManager,
    private val validator: FieldValidator,
    private val builder: ObjectBuilder
    ) {
    private var collection: ArrayDeque<Person?> = ArrayDeque()
    private var ids: ArrayList<Int> = ArrayList()

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
            val a: Person? = AddElementCommand(language, validator, builder).execute(element)
            if (a != null) {
                collection.add(a)
            }
        }
        System.out.printf(language.getString("Total") + "\n", collection.size)

        return true
    }

    fun delete(): Boolean {
        return true
    }
}