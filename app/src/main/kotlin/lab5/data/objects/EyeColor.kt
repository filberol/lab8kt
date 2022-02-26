package lab5.data.objects

import lab5.data.exceptions.EnumParseException

enum class EyeColor(private val color: String) {
    BlUE("Blue"),
    ORANGE("Orange"),
    BROWN("Brown");

    override fun toString(): String {
        return this.color
    }

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