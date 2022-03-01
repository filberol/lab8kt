package lab5.bin

import lab5.data.annotations.HardCoded
import lab5.data.commands.*
import lab5.data.utilities.*

/**
 * Console. Handles command instances and executes them with suitable arguments.
 * P.S. Здесь сыра нет!
 */
class Console(
    private val history: HistoryManager,
    private val lang: LanguageManager,
    private val collection: CollectionManager,
    private val config: ConfigManager,
    private val comparator: PersonComparator,
    private val validator: FieldValidator,
    private val builder: ObjectBuilder
) {
    init {
        println(lang.getString("Welcome") + "0.5.1 alpha")
        println("             \\|||/")
        println("             (o.o)")
        println("=---------ooO-(_)-Ooo----------=")
    }

    fun eatCommand(command: String, arguments: ArrayList<String>) {
        history.saveCommand(command)
        @HardCoded
        when (command) {
            //YA BIL ROZHDEN CHTOBY GOVNOKODIT'
            "exit" -> Exit(lang).safeExecute(arguments)
            "history" -> History(lang).safeExecute(arguments, history)
            "add" -> AddElement(lang, validator, builder, collection).safeExecute(arguments, 0)
            "clear" -> ClearCollection(lang).safeExecute(arguments, collection)
            "update" -> UpdateID(lang, validator, builder, collection).safeExecute(arguments)
            "show" -> PrintCollection(lang, collection).safeExecute(arguments, comparator.idComparator)
            "remove_by_id" -> RemoveByID(lang, collection).safeExecute(arguments)
            "save" -> SaveCollection(lang, collection).safeExecute(config.getDataPath(), arguments)
            "execute_script" -> ExecuteScript(lang, this).safeExecute(arguments)
            "remove_first" -> RemoveFirst(lang, collection).safeExecute(arguments, comparator.idComparator)
            "remove_lower" -> RemoveLower(lang, collection).safeExecute(arguments, comparator)
            "print_ascending" -> PrintCollection(lang, collection).safeExecute(arguments,comparator.reverseComparator)
            "filter_greater_than_birthday" -> Filter(lang, collection).safeExecute(arguments, comparator.reverseComparator)
            "print_field_descending_location" -> Filter(lang, collection).safeExecute(arguments, comparator.locationComparator)
            "help" -> Help(lang).safeExecute()
            else -> {
                if (config.getLogU()) {
                    history.saveCommand(command)
                }
                println("$command "+lang.getString("NoSuchCommand"))
            }
        }
    }
}