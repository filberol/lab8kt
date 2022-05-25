package lab6client.run

import lab6client.data.utilities.*
import lab6client.server.ConnectionHandler
import common.entities.User
import lab6client.data.commands.Proceed
import lab6client.gui.HomeFrame
import lab6client.gui.RegDialog
import kotlin.system.exitProcess

/**
 * @author filberol Inc.
 * @version alpha 0.5.5
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
    //Setting User data and GUI
    val waiter = Object()
    val user = User()
    val connection = ConnectionHandler(language, user, collection, waiter, config)
    //Initializing GUI
    var frame: HomeFrame? = null
    if (!args.contains("-Dmode=cli")) {
        RegDialog(user, language, connection)
        frame = HomeFrame(collection, language, user, connection, validator, builder)
    } else {
        user.readVars()
    }
    //Initializing Shell
    val console = Console(history, language, collection, config, comparator,
        validator, builder, connection, frame, user)
    frame!!.setConsole(console)
    val connThread = Thread(connection).also { it.start() }
    val userScript = InteractiveMode(console, user)
    //Checking connection
    Thread.sleep(1000)
    try {
        frame.updateCurrentTab()
    } catch (_: NullPointerException) {}
    if (connThread.isAlive) {
        if (connection.isConnected()) {
            println(language.getString("Queue"))
            userScript.start()
        } else {
            synchronized(waiter) {
                try {
                    waiter.wait()
                } catch (_: InterruptedException) {}
                if (!Proceed(language).execute()) {
                    exitProcess(0)
                } else {
                    userScript.start()
                }
            }
        }
    } else {
        userScript.start()
    }
}