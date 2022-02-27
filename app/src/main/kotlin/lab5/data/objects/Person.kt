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
    private val hairColor: HairColor
    ) {

    fun getID(): Int {
        return id
    }

    @HardCoded
    override fun toString(): String {
        return String.format("| %4s | %28s | %10s | %s | %6s | %s | %10s | %10s |",
            id, name, coordinates.toString(), creationDate, height, birthday.toString(),
            eyeColor.toString(), hairColor.toString())
    }
}