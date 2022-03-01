package lab5.data.utilities

import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.collections.ArrayList
import kotlin.math.max

/**
 * Class-history handler. Stores command, prints them.
 * Can be used to make logs.
 */
class HistoryManager(
    conf: ConfigManager,
    private val lang: LanguageManager
) {
    private var historyLog: ArrayList<String> = ArrayList()
    private var historyLength: Int = 7
    private var historyLogUnknown: Boolean = false
    init {
        try {
            historyLength = Integer.valueOf(conf.getString("HistoryLength"))
        } catch (e: NumberFormatException) {}
        historyLogUnknown = conf.getString("HistoryLogUnknown").toBoolean()
    }

    fun saveCommand(lastCommand: String) {
        historyLog.add(
            SimpleDateFormat("yyyy MM.dd HH:mm:ss")
                .format(Calendar.getInstance().time) + "\t\t" + lastCommand
        )
    }

    fun printHistory() {
        System.out.printf(lang.getString("HistHeading") + "\n", historyLength)
        if (historyLog.size < historyLength) {
            println(lang.getString("NoHistory"))
        }
        val count = historyLog.size
        historyLog.stream()
            .skip(max(count - historyLength, 0).toLong())
            .forEach { x: String? -> println(x) }
    }
}