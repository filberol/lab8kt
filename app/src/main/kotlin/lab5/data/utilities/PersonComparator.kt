package lab5.data.utilities

import lab5.data.objects.Person

/**
 * Class-storage. Gives needed comparators by its public fields.
 * Encapsulation sucks.
 */
class PersonComparator {
    val birthdayComparator = Comparator { p1: Person, p2: Person -> p1.getBirthday().compareTo(p2.getBirthday()) }
    val reverseComparator = Comparator { p1: Person, p2: Person -> p2.getBirthday().compareTo(p1.getBirthday()) }
    val locationComparator = Comparator { p1: Person, p2: Person -> p2.getLocName().compareTo(p1.getLocName()) }
    val idComparator = Comparator { p1: Person, p2: Person -> p1.getID().compareTo(p2.getID()) }
}