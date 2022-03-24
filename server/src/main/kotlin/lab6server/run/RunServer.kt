package lab6server.run

import lab6server.data.utilities.*
import lab6server.server.UserHandler

/**
 * @author filberol Inc.
 * @version alpha 0.5.3
 */
fun main() {
    //Loading language pack
    val language = LanguageManager()
    //Loading Config
    val config = ConfigManager(language)
    //Creating Object Utils
    val validator = FieldValidator()
    val builder = ObjectBuilder()
    //Loading Collection
    val collection = CollectionManager(language)
    val fileManager = CollectionFileManager(language, validator, builder, collection)
    fileManager.execute("load", config.getDataPath())
    //Starting Server
    val users = UserHandler()
    val thread = RunPortThread(language, collection, users, config)
    Thread(thread).start()
    //Initializing shell
    val console = Console(language, collection, fileManager, config, validator, builder, thread)
    while (true) {
        console.eatCommand()
    }
}