package common.objects

import java.time.LocalDate
import java.time.ZonedDateTime
import java.io.Serializable

data class Person(
    private var id: Int,
    private val name: String,
    private val coordinates: Coordinates,
    private val creationDate: ZonedDateTime,
    private val height: Int,
    private val birthday: LocalDate,
    private val eyeColor: EyeColor,
    private val hairColor: HairColor,
    private val location: Location
): Serializable {
    fun getID() = id
    fun setID(Id: Int) {id = Id}
    fun getBirthday() = birthday
    fun getLocName() = location.getName()

    override fun toString() = String.format("| %4s | %28s | %10s | %50s | %6s | %s | %10s | %10s | %32s |",
            id, name, coordinates.toString(), creationDate, height, birthday.toString(),
            eyeColor.toString(), hairColor.toString(), location.toString())

    fun serialize() = listOf(
            id.toString(), name, coordinates.getX(), coordinates.getY(), creationDate.toString(),
            height.toString(), birthday.toString(), eyeColor.toString(), hairColor.toString(),
            location.getX(), location.getY(), location.getName())
}