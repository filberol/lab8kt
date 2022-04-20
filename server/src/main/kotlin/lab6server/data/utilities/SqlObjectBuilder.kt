package lab6server.data.utilities

import common.objects.EyeColor
import common.objects.HairColor
import common.objects.Person
import java.sql.ResultSet
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.stream.Stream

class SqlObjectBuilder {
    fun streamBuiltObjects(res: ResultSet): Stream<Person> {
        val list = ArrayList<Person>()
        val builder = ObjectBuilder()
        while (res.next()) {
            list.add(builder.buildObject(arrayListOf(
                res.getInt("idNum"),
                res.getString("name"),
                res.getDouble("cordX"),
                res.getInt("cordY"),
                ZonedDateTime.parse(res.getString("crDate")),
                res.getInt("height"),
                LocalDate.parse(res.getString("birthday")),
                EyeColor.parse(res.getString("eyeColor")),
                HairColor.parse(res.getString("hairColor")),
                res.getFloat("locCordX"),
                res.getFloat("locCordY"),
                res.getString("locName"),
                res.getString("owner"),
                res.getBoolean("action")
            )))
        }
        return list.stream()
    }
}