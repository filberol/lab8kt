package lab5.bin

import lab5.data.utilities.*

/**
 * @author filberol Inc.
 * @version alpha 0.3.5
 */
fun main(args: Array<String>) {
    //Loading language pack
    val language = LanguageManager()
    //Loading Config
    val config = ConfigManager(language)
    //Initializing log
    val history = HistoryManager(config, language)
    //Loading Collection
    val collection = CollectionManager()
    //Initializing Shell
    val console = Console(history, config, language, collection)
    //Starting interactive mode
    val userScript = InteractiveMode(console, language)
    while (true) {
        userScript.commandRead()
    }
}