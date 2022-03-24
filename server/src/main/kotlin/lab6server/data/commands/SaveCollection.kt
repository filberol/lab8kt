package lab6server.data.commands

import lab6server.data.utilities.CollectionFileManager
import lab6server.data.utilities.LanguageManager

/**
 * Command saves the collection to file, from where the collection ws loaded.
 */
class SaveCollection(
    language: LanguageManager,
    private val fileManager: CollectionFileManager
): AbstractCommand(language) {
    fun execute(path: String){
        fileManager.execute("save", path)
    }
}