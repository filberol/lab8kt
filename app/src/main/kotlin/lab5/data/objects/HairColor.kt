package lab5.data.objects

import lab5.data.exceptions.EnumParseException

enum class HairColor(private val color: String) {
    RED("Red"),
    BLUE("Blue"),
    YELLOW("Yellow"),
    ORANGE("Orange");

    override fun toString(): String {
        return this.color
    }

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