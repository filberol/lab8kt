package common.entities

import common.objects.Person
import java.io.Serializable

data class Answer(
    private val element: Person?,
    private val token: String,
    private val collectionVer: Int
): Serializable {
    fun getElement() = element
    fun getToken() = token
    fun getVersion() = collectionVer
}