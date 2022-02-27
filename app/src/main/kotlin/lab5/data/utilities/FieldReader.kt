package lab5.data.utilities

import lab5.data.annotations.HardCoded
import lab5.data.annotations.UserEnter
import lab5.data.exceptions.ExecuteException
import lab5.data.objects.EyeColor
import lab5.data.objects.HairColor
import lab5.data.objects.Person
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.*
import java.util.concurrent.ExecutionException
import kotlin.collections.ArrayList
import kotlin.reflect.KClass

class FieldReader(
    private val language: LanguageManager,
    private val validator: FieldValidator,
    private val builder: ObjectBuilder
    ) {
    private val fieldList = language.getString("FieldList").split(",")
    private val degenerated: ArrayList<Any> = ArrayList()
    private val scanner = Scanner(System.`in`)

    @HardCoded
    private val userFieldTable: List<KClass<out Any>> = listOf(
        String::class,//Name
        Double::class,//CordX
        Int::class,//CordY
        Int::class,//Height
        LocalDate::class,//Birthday
        EyeColor::class,//Eye Color
        HairColor::class//Hair Color
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
        for(i in 0..6) {
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