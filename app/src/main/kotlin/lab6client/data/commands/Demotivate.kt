package lab6client.data.commands

import lab6client.data.utilities.LanguageManager

class Demotivate(
    private val language: LanguageManager
): AbstractCommand(
    language
) {
    fun execute(arguments: ArrayList<String>) {
        if (arguments.isEmpty() || (arguments.isNotEmpty().also { println(language.getString("RedundantArgs")) }
                    && Proceed(language).execute())) {
            println(
                """+---------------------------------------------------------+
                   ||                                                         |
                   ||    +-----------------------------------------------+    |
                   ||    |                                               |    |
                   ||    |    +-------------------------------------+    |    |
                   ||    |    |                                     |    |    |
                   ||    |    |                                     |    |    |
                   ||    |    |         \  /    \  /   ----         |    |    |
                   ||    |    |          \/      \/    |  |         |    |    |
                   ||    |    |          /\      /     |  |         |    |    |
                   ||    |    |         /  \    /     ------        |    |    |
                   ||    |    |                       |     \       |    |    |
                   ||    |    |                                     |    |    |
                   ||    |    |                                     |    |    |
                   ||    |    |                                     |    |    |
                   ||    |    +-------------------------------------+    |    |
                   ||    |              ASCII-демотиватор                |    |
                   ||    |       Что дальше? Демотиватор в коде?         |    |
                   ||    +-----------------------------------------------+    |
                   ||                          нет                            |
                   ||              Демотиватор в лабе по проге.               |
                   |+---------------------------------------------------------+
                """.trimMargin()
            )
        }
    }
}