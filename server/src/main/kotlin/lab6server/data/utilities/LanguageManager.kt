package lab6server.data.utilities

import java.util.*

/**
 * Class holds ResourceBundle with localisation.
 * Different languages to be realized.
 */
class LanguageManager {
    private lateinit var language: ResourceBundle
    init {
        setLanguage()
    }

    private fun setLanguage() {
        val locale = Locale("en","US")
        language = ResourceBundle.getBundle("lang/localization", locale)
    }
    fun setLanguage(locale: String) {
        language = ResourceBundle.getBundle("lang/localization", Locale(locale))
    }

    infix fun getString(s: String) = language.getString(s) ?: String()
}