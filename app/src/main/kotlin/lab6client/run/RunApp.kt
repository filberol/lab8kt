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
    val connThread = Thread(connection)
    connThread.start()
    //Initializing Shell
    val console = Console(history, language, collection, config, comparator, validator, builder, connection)
    //Checking connection
    Thread.sleep(1000)
    if (connThread.isAlive) {
        if (connection.isConnected()) {
            println(language.getString("Queue"))
            //Starting interactive mode
            val userScript = InteractiveMode(console, user)
            while (true) {
                userScript.commandLineRead()
            }
        }
    } else {
        //Starting interactive mode
        val userScript = InteractiveMode(console, user)
        while (true) {
            userScript.commandLineRead()
        }
    }

}