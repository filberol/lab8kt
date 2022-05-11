package common.objects

import java.time.LocalDate
import java.time.ZonedDateTime
import java.io.Serializable

data class Person(
    private var Id: Int,
    private val Name: String,
    private val Coordinates: Coordinates,
    private val CreationDate: ZonedDateTime,
    private val Height: Int,
    private val Birthday: LocalDate,
    private val EyeColor: EyeColor,
    private val HairColor: HairColor,
    private val Location: Location,
    private val Owner: String,
    private var action: Boolean
): Serializable {
    fun getID() = Id
    fun setID(Id: Int) {
        this.Id = Id}
    fun getName() = Name
    fun getBirthday() = Birthday
    fun getLocName() = Location.getName()
    fun getAction() = action
    fun setAction(a: Boolean) {action = a}

    fun toTable() = String.format("| %4s | %28s | %10s | %50s | %6s | %s | %10s | %10s | %32s |",
            Id, Name, Coordinates.toTable(), CreationDate, Height, Birthday.toString(),
            EyeColor.toString(), HairColor.toString(), Location.toTable())

    fun serialize(): List<String> = listOf(
            Id.toString(), Name, Coordinates.getX().toString(), Coordinates.getY().toString(),
            CreationDate.toString(), Height.toString(), Birthday.toString(), EyeColor.toString(),
            HairColor.toString(), Location.getX(), Location.getY(), Location.getName(), Owner,
            action.toString())
}