package lab6client.data.utilities

import common.objects.EyeColor
import common.objects.HairColor
import lab6client.data.annotations.HardCoded
import java.time.LocalDate
import kotlin.reflect.KClass

class FieldContainer {
    /**
     * Automated fields order
     */
    @HardCoded
    private val userFieldTable: List<KClass<out Any>> = listOf(
        String::class,      //Name
        Double::class,      //CordX
        Int::class,         //CordY
        Int::class,         //Height
        LocalDate::class,   //Birthday
        EyeColor::class,    //Eye Color
        HairColor::class,   //Hair Color
        Float::class,       //CordX
        Float::class,       //CordY
        String::class       //LocName
    )

    fun getFieldTable() = userFieldTable
}