package lab5.data.objects

import lab5.data.annotations.HardCoded
import java.time.LocalDate
import java.time.ZonedDateTime

data class Person(
    private val id: Int,
    private val name: String,
    private val coordinates: Coordinates,
    private val creationDate: ZonedDateTime,
    private val height: Int,
    private val birthday: LocalDate,
    private val eyeColor: EyeColor,
    private val hairColor: HairColor,
    private val location: Location
    ) {

    fun getID(): Int {
        return id
    }

    fun getBirthday(): LocalDate {
        return birthday
    }

    fun getLocName(): String {
        return location.getName()
    }

    @HardCoded
    override fun toString(): String {
        return String.format("| %4s | %28s | %10s | %50s | %6s | %s | %10s | %10s | %32s |",
            id, name, coordinates.toString(), creationDate, height, birthday.toString(),
            eyeColor.toString(), hairColor.toString(), location.toString())
    }
    @HardCoded
    fun serialize(): String {
        return String.format("%d,%s,%s,%s,%d,%s,%s,%s,%s",
            id, name, coordinates.serialize(), creationDate.toString(), height,
            birthday.toString(), eyeColor.toString(), hairColor.toString(), location.serialize())
    }
}