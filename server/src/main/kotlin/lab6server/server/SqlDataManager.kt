package lab6server.server

import java.sql.Connection

/**
 * Class provides connection with database elements table.
 * Table consists of history of adds and removals.
 */
class SqlDataManager(
    private val connection: Connection
) {

}