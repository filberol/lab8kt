package lab6client.data.utilities

import java.io.InputStreamReader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.Properties
import java.util.Locale


/**
 * Class holds PROPERTY FILE WITH UTF-8 SUPPORTED with localisation.
 */
class LanguageManager {
    private var langPackMap: Properties = Properties()
    private val langSource = "app/src/main/resources/lang/localization"
    private var locale = Locale.getDefault().toString()

    init {
        setLanguage(locale)
    }

    fun setLanguage(localeIn: String) {
        try {
            langPackMap.load(InputStreamReader(FileInputStream("${langSource}_$localeIn.config")))
        } catch (e: FileNotFoundException) {
            println("Language File does not exist")
        } catch (e: IOException) {
            println("Cannot load Language Pack")
        }
    }

    infix fun getString(s: String): String = langPackMap.getProperty(s)
}