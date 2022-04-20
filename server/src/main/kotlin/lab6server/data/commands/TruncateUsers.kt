package lab6server.data.commands

import lab6server.data.utilities.CollectionManager
import lab6server.data.utilities.LanguageManager


class TruncateUsers(
    private val language: LanguageManager,
    private val collection: CollectionManager
): AbstractCommand(language) {
    fun execute(arguments: ArrayList<String>) {
        if (arguments.isEmpty() || (arguments.isNotEmpty().also { println(language.getString("RedundantArgs")) }
                    && Proceed(language).execute())) {
            collection.getDbHandler().userManager.truncateUsers()
        }
    }
}