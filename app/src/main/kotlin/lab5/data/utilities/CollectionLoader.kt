package lab5.data.utilities

import lab5.data.annotations.FileReader
import lab5.data.annotations.HardCoded
import lab5.data.commands.AddElement
import lab5.data.parsers.CsvParser
import java.io.FileNotFoundException
import java.io.IOException

/**
 * Class-loader. Interprets parsed data sends it to construction, uses standard user command to add constructed elements.
 * Firstly was stored in CollectionManager
 */
class CollectionLoader(
    private val language: LanguageManager,
    private val validator: FieldValidator,
    private val builder: ObjectBuilder,
    private val collection: CollectionManager
) {
    @FileReader
    @Throws(IOException::class)
    fun loadCollection(path: String): Boolean {
        println(language.getString("CollectionLoad"))
        val serializedCollection: ArrayList<Array<String>> = CsvParser().readCollection(path)

        for (element: Array<String> in serializedCollection) {
            @HardCoded
            if (element.size == 12) {
                AddElement(language, validator, builder, collection).execute(element)
            }
        }
        System.out.printf(language.getString("LoadCount") + "\n", collection.getSize())
        return true
    }

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
        return true
    }
}