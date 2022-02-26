package lab5.data.commands

import lab5.data.exceptions.ParseException
import lab5.data.objects.EyeColor
import lab5.data.objects.HairColor
import lab5.data.objects.Person
import lab5.data.utilities.FieldValidator
import lab5.data.utilities.LanguageManager
import lab5.data.utilities.ObjectBuilder
import java.time.LocalDate
import java.time.ZonedDateTime
import kotlin.reflect.KClass

class AddElementCommand(
    private val language: LanguageManager,
    private val validator: FieldValidator,
    private val builder: ObjectBuilder
): Command(language) {
    private val fieldTable: List<KClass<out Any>> = listOf(
        Int::class,
        String::class,
        Double::class,
        Int::class,
        ZonedDateTime::class,
        Int::class,
        LocalDate::class,
        EyeColor::class,
        HairColor::class
    )

    fun execute(arguments: Array<String>): Person? {
        val degenerated: ArrayList<Any> = ArrayList()
        for(i in 0..8) {
            try {
                degenerated.add(validator.parseField(arguments[i],fieldTable[i]))
            } catch (e: ParseException) {
                System.out.printf(language.getString("ParseException") + "\n", arguments[i])
                return null
            }
        }
        return builder.buildObject(degenerated)
    }

    override fun safeExecute(arguments: ArrayList<String>): Boolean {
        return true
    }
}