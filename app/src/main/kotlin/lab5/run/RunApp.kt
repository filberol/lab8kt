package lab5.run

import lab5.data.utilities.*

/**
 * @author filberol Inc.
 * @version alpha 0.5.3
 */
fun main(args: Array<String>) {
    //Loading language pack
    val language = LanguageManager()
    //Loading Config
    val config = ConfigManager(language)
    //Initializing log
    val history = HistoryManager(config, language)
    //Creating Object Utils
    val validator = FieldValidator()
    val builder = ObjectBuilder()
    val comparator = PersonComparator()
    //Loading Collection
    val collection = CollectionManager(language)
    val fileManager = CollectionFileManager(language, validator, builder, collection)
    if (args.isNotEmpty()) config.setDataPath(args[0])
    fileManager.execute("load", config.getDataPath())
    //Initializing Shell
    val console = Console(history, language, collection, fileManager, config, comparator, validator, builder)
    //Starting interactive mode
    val userScript = InteractiveMode(console)
    while (true) {
        userScript.commandRead()
    }
}