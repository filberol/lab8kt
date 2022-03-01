package lab5.data.utilities

import java.util.*

/**
 * Class holds ResourceBundle with localisation.
 * Different languages to be realized.
 */
class LanguageManager {
    private var language: ResourceBundle? = null
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

    fun getString(s: String): String {
        return language?.getString(s) ?: String()
    }
}