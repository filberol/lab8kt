package common.entities

import common.objects.Person
import java.io.Serializable

data class Request(
    private val user: User,
    private val element: Person?,
    private val collVer: Int,
    private val register: Boolean = false
): Serializable {
    fun getUser() = user
    fun geElement() = element
    fun getVer() = collVer
    fun getRegister() = register
}