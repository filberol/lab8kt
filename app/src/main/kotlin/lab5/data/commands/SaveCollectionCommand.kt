package lab5.data.commands

import lab5.data.annotations.FileWriter
import lab5.data.utilities.BufferedCollectionWriter
import lab5.data.utilities.CollectionManager
import lab5.data.utilities.LanguageManager
import java.io.FileNotFoundException
import java.io.IOException

class SaveCollectionCommand(
    private val language: LanguageManager,
    private val collection: CollectionManager
): AbstractCommand(language) {

    @FileWriter
    fun execute(path: String, deserialized: ArrayList<String>): Boolean {
        BufferedCollectionWriter(path).writeCollection(deserialized)
        return true
    }

    fun safeExecute(path: String, arguments: ArrayList<String>): Boolean {
        if (arguments.isEmpty() || (arguments.isNotEmpty() && ProceedCommand(language).safeExecute())) {
            try {
                execute(path, collection.serialize())
                println(language.getString("Done"))
            } catch (e: FileNotFoundException) {
                println(path + language.getString("DataFileNotFound"))
            } catch (e: SecurityException) {
                println(path + language.getString("DataNoAccess"))
            } catch (e: IOException) {
                println(path + language.getString("DataIOError"))
            }
            return true
        }
        return false
    }
}