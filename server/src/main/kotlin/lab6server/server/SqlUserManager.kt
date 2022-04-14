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
    fun checkAddUser(user: User): Boolean {
        val stat = connection.createStatement()
        val query = "SELECT COUNT(*) FROM users WHERE username = '${user.getLogin()}' AND password = '${user.getPass()}';"
        val res = stat.executeQuery(query)
        res.next()
        println("users found " + res.getInt("count"))
        if (res.getInt("count") == 1) {
            stat.close()
            res.close()
            return true
        } else {

            val addSt = "INSERT INTO users VALUES ('${user.getLogin()}', '${user.getPass()}');"
            stat.execute(addSt)
        }
        return false
    }

    fun dropUsers() {
        val stat = connection.createStatement()
        stat.execute("DROP TABLE users")
    }
}