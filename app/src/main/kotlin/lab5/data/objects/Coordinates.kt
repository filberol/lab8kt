package lab5.data.objects

import lab5.data.annotations.HardCoded

data class Coordinates(
    private val x: Double = 0.0,
    private val y: Int = 0
) {
    @HardCoded
    override fun toString() = String.format("%3.2f %6d", x, y)

    fun getX() = x.toString()
    fun getY() = y.toString()
}