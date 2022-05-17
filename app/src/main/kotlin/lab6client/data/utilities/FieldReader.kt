package lab6client.data.utilities

import common.entities.User
import lab6client.data.annotations.HardCoded
import lab6client.data.annotations.UserEnter
import common.objects.*
import java.time.ZonedDateTime
import java.util.Scanner
import kotlin.collections.ArrayList

/**
 * Class holds fields to ask from user to construct the object.
 * Used for interactive input of the Object.
 */
class FieldReader(
    private val language: LanguageManager,
    private val validator: FieldValidator,
    private val builder: ObjectBuilder,
    private val user: User
    ) {
    private val fieldList = language.getString("FieldList").split(",")
    private val degenerated: ArrayList<Any> = ArrayList()
    private val scanner = Scanner(System.`in`)

    private val userFieldTable = FieldContainer().getFieldTable()

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
        degenerated.add(12, user.getLogin())

        return builder.buildObject(degenerated)
    }

}