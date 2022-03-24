package common.objects

import java.io.Serializable

data class Location(
    private val x: Float,
    private val y: Float,
    private val name: String
): Serializable {
    override fun toString(): String {
        return String.format("%3.2f %3.2f | %25s", x, y, name)
    }
    fun getX() = x.toString()
    fun getY() = y.toString()
    fun getName() = name
}