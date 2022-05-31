package lab6client.data.utilities

import java.io.FileNotFoundException
import java.io.IOException
import java.util.Locale
import java.util.MissingResourceException
import java.util.ResourceBundle


/**
 * Class holds ListResourceBundle managment
 */
class LanguageManager {
    private var locale = Locale.getDefault()
    private lateinit var bundle: ResourceBundle

    init {
        setLanguage(locale)
    }

    fun setLanguage(localeIn: Locale) {
        try {
            bundle = ResourceBundle.getBundle("lab6client.lang.localization", localeIn)
        } catch (e: FileNotFoundException) {
            println("Language File does not exist")
        } catch (e: IOException) {
            println("Cannot load Language Pack")
        }
    }

    infix fun getString(s: String): String {
        return try {
            bundle.getString(s)
        } catch (e: MissingResourceException) {
            "PlaceholderString"
        }
    }
}