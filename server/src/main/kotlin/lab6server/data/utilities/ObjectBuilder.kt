package lab6server.data.utilities

import common.objects.*
import java.time.LocalDate
import java.time.ZonedDateTime

/**
 * Simple class to construct and return object.
 * All the fields should be parsed and validated by the moment it is used.
 */
class ObjectBuilder {
    fun buildObject(l: ArrayList<Any>): Person {
        return Person(
            l[0] as Int,
            l[1] as String,
            Coordinates(
                l[2] as Double,
                l[3] as Int
            ),
            l[4] as ZonedDateTime,
            l[5] as Int,
            l[6] as LocalDate,
            l[7] as EyeColor,
            l[8] as HairColor,
            Location(
                l[9] as Float,
                l[10] as Float,
                l[11] as String
            ),
            l[12] as String,
            l[13] as Boolean
        )
    }
}