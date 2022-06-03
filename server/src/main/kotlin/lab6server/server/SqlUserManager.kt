package lab6server.server

import common.entities.User
import java.sql.Connection

/**
 * Class handles database user table queries.
 */
class SqlUserManager(
    private val connection: Connection
) {
    /**
     * Check and if not exists add user.
     */
    fun checkUserPassword(user: User): Boolean {
        val query = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?;"
        val stat = connection.prepareStatement(query)
        stat.setString(1, user.getLogin())
        stat.setString(2, user.getPass())
        val res = stat.executeQuery()
        res.next()
        println("Users found " + res.getInt("count"))
        return res.getInt("count") == 1
    }

    fun checkUserExist(user: User): Boolean {
        val query = "SELECT COUNT(*) FROM users WHERE username = ?;"
        val stat = connection.prepareStatement(query)
        stat.setString(1, user.getLogin())
        val res = stat.executeQuery()
        res.next()
        return res.getInt("count") == 1
    }

    fun addUser(user: User): Boolean {
        val addString = "INSERT INTO users VALUES (?, ?);"
        val addStat = connection.prepareStatement(addString)
        addStat.setString(1, user.getLogin())
        addStat.setString(2, user.getPass())
        val res = addStat.executeUpdate()
        return res == 1
    }

    fun truncateUsers() {
        connection.createStatement().execute("TRUNCATE TABLE users;")
    }
}
