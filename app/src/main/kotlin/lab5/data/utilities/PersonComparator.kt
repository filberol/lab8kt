package lab5.data.utilities

import lab5.data.objects.Person

class PersonComparator {
    val birthdayComparator = Comparator { p1: Person, p2: Person -> p1.getBirthday().compareTo(p2.getBirthday())}
    val lengthComparator = Comparator { str1: String, str2: String -> str1.length - str2.length }
    val idComparator = Comparator { p1: Person, p2: Person -> p1.getID().compareTo(p2.getID())}
}