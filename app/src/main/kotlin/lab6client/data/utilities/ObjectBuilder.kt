package lab6client.data.utilities

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
            l[0] as Int,            //ID
            l[1] as String,         //Name
            Coordinates(
                l[2] as Double,     //CordX
                l[3] as Int         //CordY
            ),
            l[4] as ZonedDateTime,  //Creation Date
            l[5] as Int,            //Height
            l[6] as LocalDate,      //Birthday
            l[7] as EyeColor,       //Eye Color
            l[8] as HairColor,      //Hair Color
            Location(
                l[9] as Float,      //LocCordX
                l[10] as Float,     //LocCordY
                l[11] as String     //LocName
            ),
            l[12] as String,        //Owner
            true              //Client only build add objects
        )
    }
}