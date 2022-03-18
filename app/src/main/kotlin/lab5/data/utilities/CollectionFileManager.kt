package lab5.data.utilities

import lab5.data.commands.AddElement
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.io.FileNotFoundException
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVPrinter

/**
 * Class-loader-saver. Interprets parsed data sends it to construction, uses standard user command to add constructed elements.
 * Firstly was stored in CollectionManager
 */
class CollectionFileManager(
    private val language: LanguageManager,
    private val validator: FieldValidator,
    private val builder: ObjectBuilder,
    private val collection: CollectionManager
) {
    fun execute(method: String, path: String) {
        try {
            when (method) {
                "save" -> saveCollection(path)
                "load" -> loadCollection(path)
            }
        } catch (e: FileNotFoundException) {
            println(path + language.getString("DataFileNotFound"))
        } catch (e: SecurityException) {
            println(path + language.getString("DataNoAccess"))
        } catch (e: IOException) {
            println(path + language.getString("DataIOError"))
        }
    }


    private fun loadCollection(path: String) {
        println(language.getString("CollectionLoad"))
        val file = FileReader(path)
        val parser = CSVParser(file, CSVFormat.DEFAULT.withTrim())
        for (record in parser) {
            AddElement(language, validator, builder, collection).addFromRecord(record)
        }
    }

    private fun saveCollection(path: String) {
        val file = FileWriter(path)
        val saver = CSVPrinter(file, CSVFormat.DEFAULT)
        collection.getSerializedStream().forEach(saver::printRecord)
        println(language.getString("Done"))
        saver.flush()
        saver.close()
    }
}