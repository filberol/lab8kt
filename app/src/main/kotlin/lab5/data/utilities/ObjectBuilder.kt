package lab5.data.utilities

import lab5.data.objects.Coordinates
import lab5.data.objects.EyeColor
import lab5.data.objects.HairColor
import lab5.data.objects.Person
import java.time.LocalDate
import java.time.ZonedDateTime

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
            l[8] as HairColor
        )
    }
}