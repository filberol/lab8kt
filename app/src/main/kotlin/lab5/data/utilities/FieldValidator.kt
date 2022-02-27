package lab5.data.utilities

import lab5.data.exceptions.ParseException
import lab5.data.objects.EyeColor
import lab5.data.objects.HairColor
import java.lang.Exception
import java.time.LocalDate
import java.time.ZonedDateTime
import kotlin.reflect.KClass

class FieldValidator {
    private val table: HashMap<KClass<out Any>, (String) -> Any> = hashMapOf(
        Int::class to {x: String -> x.toInt()},
        String::class to {x: String -> x},
        Double::class to String::toDouble,
        ZonedDateTime::class to {x: String -> ZonedDateTime.parse(x)},
        LocalDate::class to {x: String -> LocalDate.parse(x)},
        EyeColor::class to {x: String -> EyeColor.parse(x)},
        HairColor::class to {x: String -> HairColor.parse(x)}
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