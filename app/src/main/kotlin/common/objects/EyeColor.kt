package common.objects

import common.exceptions.EnumParseException
import java.io.Serializable

enum class EyeColor(private val color: String): Serializable {
    BlUE("Blue"),
    ORANGE("Orange"),
    BROWN("Brown");

    override fun toString() = color

    companion object Parser {
        fun parse(x: String): EyeColor {
            return when (x.lowercase()) {
                "blue" -> BlUE
                "orange" -> ORANGE
                "brown" -> BROWN
                else -> throw EnumParseException("ParseException")
            }

        }
    }
}