package lab5.data.commands

import lab5.data.objects.Person
import lab5.data.utilities.CollectionManager
import lab5.data.utilities.LanguageManager

data class SortCollectionCommand(
    private val language: LanguageManager,
    private val collection: CollectionManager
) {
    val birthdayComparator = Comparator { p1: Person, p2: Person -> p1.getBirthday().compareTo(p2.getBirthday())}
    val lengthComparator = Comparator { str1: String, str2: String -> str1.length - str2.length }
    val idComparator = Comparator { p1: Person, p2: Person -> p1.getID().compareTo(p2.getID())}
}