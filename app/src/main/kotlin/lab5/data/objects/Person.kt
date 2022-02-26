package lab5.data.objects

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
    )