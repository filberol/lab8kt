package lab6server.run

import lab6server.data.utilities.*
import lab6server.data.utilities.TokenManager

/**
 * @author filberol Inc.
 * @version alpha 0.5.5
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
    val collection = CollectionManager(language, config)
    val fileManager = CollectionFileManager(language, validator, builder, collection)
    fileManager.execute("load", config.getDataPath())
    //Starting Server
    val users = TokenManager()
    val thread = RunServerThread(language, collection, users, config)
    Thread(thread, "SocketAcceptor").start()
    //Initializing shell
    val console = Console(language, collection, fileManager, config, validator, builder, thread)
    while (true) {
        console.eatCommand()
    }
}