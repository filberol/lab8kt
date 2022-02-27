package lab5.data.commands

import lab5.data.annotations.*
import lab5.data.exceptions.FullCollectionException
import lab5.data.exceptions.ParseException
import lab5.data.objects.EyeColor
import lab5.data.objects.HairColor
import lab5.data.utilities.*
import java.time.LocalDate
import java.time.ZonedDateTime
import kotlin.collections.ArrayList
import kotlin.reflect.KClass

class AddElementCommand(
    private val language: LanguageManager,
    private val validator: FieldValidator,
    private val builder: ObjectBuilder,
    private val collection: CollectionManager
): Command(language) {

    @HardCoded
    private val fieldTable: List<KClass<out Any>> = listOf(
        Int::class,//ID
        String::class,//Name
        Double::class,//CordX
        Int::class,//CordY
        ZonedDateTime::class,//Creation
        Int::class,//Height
        LocalDate::class,//Birthday
        EyeColor::class,//Eye Color
        HairColor::class//Hair Color
    )

    fun execute(arguments: Array<String>): Boolean {
        val degenerated: ArrayList<Any> = ArrayList()
        for(i in 0..8) {
            try {
                degenerated.add(validator.parseField(arguments[i],fieldTable[i])!!)
            } catch (e: ParseException) {
                System.out.printf(language.getString("ParseException") + "\n", arguments[i])
                return false
            }
        }
        return collection.addNotNull(builder.buildObject(degenerated))
    }

    fun safeExecute(arguments: ArrayList<String>, id: Int): Boolean {
        if (arguments.isEmpty() || (arguments.isNotEmpty() && ProceedCommand(language).safeExecute())) {
            try {
                collection.addNotNull(FieldReader(language, validator, builder)
                        .askFields(when (id) {0 -> collection.getFreeID() else -> id}))
                println(language.getString("Done"))
            } catch (e: FullCollectionException) {
                println(language.getString("ExecuteException"))
                return false
            }
        }
        return true
    }
}