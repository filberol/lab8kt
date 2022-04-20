package lab6server.server

import lab6server.data.utilities.CollectionManager
import lab6server.data.utilities.ConfigManager
import lab6server.data.utilities.LanguageManager
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import kotlin.system.exitProcess

/**
 * Class provides basic connection with database.
 * Program cannot work without database.
 * Database holds user table and element history table, for each has a different manager.
 */
class SqlHandler(
    private val language: LanguageManager,
    private val config: ConfigManager,
    collection: CollectionManager
) {
    private val dbConnection: Connection by lazy { settleDatabaseConnection() }
    val dataManager = SqlDataManager(dbConnection, collection)
    val userManager = SqlUserManager(dbConnection)


    private fun settleDatabaseConnection(): Connection {
        val databaseDriver = "org.postgresql.Driver"
        Class.forName(databaseDriver)
        try {
            return DriverManager.getConnection(
                config.getString("dbAddress"),
                config.getString("dbUsername"),
                config.getString("dbPassword")
            )
        } catch (e: SQLException) {
            println(language.getString("DBError"))
            exitProcess(1)
        }

    }
}