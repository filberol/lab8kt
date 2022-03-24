package lab6server.data.commands

import common.annotations.HardCoded
import common.annotations.ServerCommand
import lab6server.data.utilities.*
import java.time.LocalDate
import java.time.ZonedDateTime
import kotlin.reflect.KClass
import org.apache.commons.csv.CSVRecord
import common.objects.EyeColor
import common.objects.HairColor

/**
 * Big class with HardCoded elements.
 * Read input and add to collection or imitate it, adding from file.
 */
@ServerCommand
class FileAdd(
    private val language: LanguageManager,
    private val validator: FieldValidator,
    private val builder: ObjectBuilder,
    private val collection: CollectionManager,
): AbstractCommand(language) {

    /**
     * Order of fields to be read from file to construct an object.
     */
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
        HairColor::class,//Hair Color
        Float::class,//CordX
        Float::class,//CordY
        String::class//LocName
    )

    /**
     * Constructing object from collection file
     */
    fun addFromRecord(record: CSVRecord): Boolean {
        val degenerated: ArrayList<Any> = ArrayList()
        try {
            for (i in 0..11) {
                try {
                    degenerated.add(validator.parseField(record.get(i), fieldTable[i])!!)
                } catch (e: Exception) {
                    System.out.printf(language getString "ParseException" + "\n", record.get(i))
                    return false
                }
            }
        } catch (e: IndexOutOfBoundsException) {
        println(language getString "ParseException2")
        return false
    }
        return collection.addNotNull(builder.buildObject(degenerated))
    }
}