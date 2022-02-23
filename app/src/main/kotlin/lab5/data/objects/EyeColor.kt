package lab5.data.objects

enum class EyeColor(private val color: String) {
    BlUE("Blue"),
    ORANGE("Orange"),
    BROWN("Brown");
    override fun toString(): String {
        return this.color
    }
}