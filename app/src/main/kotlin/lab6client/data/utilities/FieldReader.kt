package lab6client.data.utilities

import lab6client.data.annotations.HardCoded
import lab6client.data.annotations.UserEnter
import common.objects.*
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.Scanner
import kotlin.collections.ArrayList
import kotlin.reflect.KClass

/**
 * Class holds fields to ask from user to construct the object.
 */
class FieldReader(
    private val language: LanguageManager,
    private val validator: FieldValidator,
    private val builder: ObjectBuilder
    ) {
    private val fieldList = language.getString("FieldList").split(",")
    private val degenerated: ArrayList<Any> = ArrayList()
    private val scanner = Scanner(System.`in`)

    /**
     * Automated fields order
     */
    @HardCoded
    private val userFieldTable: List<KClass<out Any>> = listOf(
        String::class,//Name
        Double::class,//CordX
        Int::class,//CordY
        Int::class,//Height
        LocalDate::class,//Birthday
        EyeColor::class,//Eye Color
        HairColor::class,//Hair Color
        Float::class,//CordX
        Float::class,//CordY
        String::class//LocName
    )

    @UserEnter
    fun readValue(i: Int): Any? {
        print(language.getString("TypeField") + "  ")
        fieldList[i].split("/").forEach{println(it)}
        return try {
            validator.parseField(scanner.nextLine(), userFieldTable[i])
        } catch (e: Exception) {
            println(language.getString("SyntaxException"))
            readValue(i)
        }
    }

    fun askFields(id: Int): Person? {
        @HardCoded
        degenerated.add(0,id)
        for(i in 0..9) {
            val a = readValue(i)
            if (a == null){
               return null
            } else {
                degenerated.add(a)
            }
        }
        degenerated.add(4, ZonedDateTime.now())

        return builder.buildObject(degenerated)
    }

}