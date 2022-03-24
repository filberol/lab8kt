package lab6client.data.utilities

import lab6client.data.exceptions.ParseException
import common.objects.EyeColor
import common.objects.HairColor
import java.lang.Exception
import java.time.LocalDate
import java.time.ZonedDateTime
import kotlin.reflect.KClass

/**
 * Class stores the values and their methods to parse.
 * Validates the fields by throwing exceptions.
 */
class FieldValidator {
    private val table: HashMap<KClass<out Any>, (String) -> Any> = hashMapOf(
        Int::class to {x: String -> x.toInt()},
        String::class to {x: String -> x},
        Double::class to String::toDouble,
        ZonedDateTime::class to {x: String -> ZonedDateTime.parse(x)},
        LocalDate::class to {x: String -> LocalDate.parse(x)},
        EyeColor::class to {x: String -> EyeColor.parse(x)},
        HairColor::class to {x: String -> HairColor.parse(x)},
        Float::class to String::toFloat
    )

    fun parseField(to: String?, out: KClass<out Any>): Any? {
        val func = table[out]!!
        return try {
            when (to) {null -> null else -> func.invoke(to)}
        } catch (e: Exception) {
            throw ParseException(to!!)
        }
    }
}