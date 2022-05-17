package lab6client.data.utilities

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
        language = ResourceBundle.getBundle("lang/localization", locale)//ПОСТАВЬ НОРМАЛЬНУЮ ЛОКАЛЬ
    }
    fun setLanguage(locale: String) {
        language = ResourceBundle.getBundle("lang/localization", Locale(locale))
    }

    infix fun getString(s: String): String {
        return try {
            language.getString(s)
        } catch (e: MissingResourceException) {
            "PlaceHolderString"
        }
    }
}