package lab6client.run

import lab6client.data.utilities.*
import lab6client.server.ConnectionHandler
import common.entities.User

/**
 * @author filberol Inc.
 * @version alpha 0.5.5
 */
fun main() {
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
    //Setting connection
    val user = User()
    user.setVars()
    val connection = ConnectionHandler(language, user, collection, config)
    connection.tryToConnect()
    //Initializing Shell
    val console = Console(history, language, collection, config, comparator, validator, builder, connection)
    //Starting interactive mode
    val userScript = InteractiveMode(console)
    while (true) {
        userScript.commandLineRead()
    }
}