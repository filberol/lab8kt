package common.objects

import common.exceptions.EnumParseException
import java.io.Serializable

enum class HairColor(private val color: String): Serializable {
    RED("Red"),
    BLUE("Blue"),
    YELLOW("Yellow"),
    ORANGE("Orange");

    override fun toString() = color

    companion object Parser {
        fun parse(x: String): HairColor {
            return when (x.lowercase()) {
                "red" -> RED
                "blue" -> BLUE
                "yellow" -> YELLOW
                "orange" -> ORANGE
                else -> throw EnumParseException("ParseException")
            }
        }
    }
}