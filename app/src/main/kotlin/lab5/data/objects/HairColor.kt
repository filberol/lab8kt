package lab5.data.objects

enum class HairColor(private val color: String) {
    RED("Red"),
    BLUE("Blue"),
    YELLOW("Yellow"),
    ORANGE("Orange");
    override fun toString(): String {
        return this.color
    }
}