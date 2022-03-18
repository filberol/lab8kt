package lab5.data.commands

import lab5.data.utilities.CollectionFileManager
import lab5.data.utilities.LanguageManager

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