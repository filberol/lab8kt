package lab6server.server

import common.annotations.HardCoded
import common.objects.Person
import lab6server.data.utilities.CollectionManager
import lab6server.data.utilities.SqlObjectBuilder
import java.sql.Connection
import java.util.stream.Stream

/**
 * Class provides connection with database elements table.
 * Table consists of history of adds and removals.
 */
class SqlDataManager(
    private val connection: Connection,
    private val collection: CollectionManager
) {
    private val builder = SqlObjectBuilder()
    @HardCoded
    fun addToDb(element: Person): Boolean {
        val ser = element.serialize()
        val addString = "INSERT INTO gitData VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
        val addStat = connection.prepareStatement(addString)
        addStat.apply {
            setInt(1, collection.getVersion())  //CollVersion
            setInt(2, ser[0].toInt())           //IdNum
            setString(3, ser[1])                //Name
            setDouble(4, ser[2].toDouble())     //CordX
            setInt(5, ser[3].toInt())           //CordY
            setString(6, ser[4])                //CrDate
            setInt(7, ser[5].toInt())           //Height
            setString(8, ser[6])                //Birthday
            setString(9, ser[7])                //EyeColor
            setString(10, ser[8])               //HairColor
            setDouble(11, ser[9].toDouble())    //LocCordX
            setDouble(12, ser[10].toDouble())   //LocCordY
            setString(13, ser[11])              //LocName
            setString(14, ser[12])              //Owner
            setBoolean(15, ser[13].toBoolean()) //Action
        }
        val res = addStat.executeUpdate()
        return res == 1
    }

    fun streamDiff(collVer: Int): Stream<Person> {
        val query = "SELECT * FROM gitData WHERE collVer > ? ORDER BY collVer;"
        val stat = connection.prepareStatement(query)
        stat.setInt(1, collVer)
        val res = stat.executeQuery()
        return builder.streamBuiltObjects(res)
    }

    fun findLastById(id: Int): Person {
        val query = "SELECT * FROM gitData WHERE idNum = ? ORDER BY collVer DESC LIMIT 1"
        val stat = connection.prepareStatement(query)
        stat.setInt(1, id)
        val res = stat.executeQuery()
        val list = builder.streamBuiltObjects(res).toList()
        return list[0]
    }

    fun truncateData() {
        connection.createStatement().execute("TRUNCATE TABLE gitData")
    }
}