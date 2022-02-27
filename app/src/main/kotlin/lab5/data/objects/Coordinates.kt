package lab5.data.objects

data class Coordinates(
    private val x: Double = 0.0,
    private val y: Int = 0
) {
    override fun toString(): String {
        return String.format("%3.2f %6d", x, y)
    }
}