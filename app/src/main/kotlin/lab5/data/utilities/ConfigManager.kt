package lab5.data.utilities

import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.util.Properties

/**
 * Class stores the config for client, saved in file.
 * Supports common values.
 */
class ConfigManager(private val language: LanguageManager) {
    private var config: Properties = Properties()
    init {
        setDefaults()
        try {
            val stream = FileInputStream("./app/src/main/resources/app.config")
            val reader = InputStreamReader(stream)
            config.load(reader)
        } catch (e: FileNotFoundException) {
            println(language.getString("ConfigCorrupt"))
        } catch (e: IOException) {
            println(language.getString("ConfigError"))
        }
        changeLanguage(config.getProperty("Language"))
    }
    private fun setDefaults() {
        config["Language"] = "en"
        config["Mode"] = "standalone"
        config["HistoryLength"] = "7"
        config["HistoryLog"] = "true"
        config["HistoryLogUnknown"] = "false"
        config["CollectionPath"] = "./app/src/main/resources/"
    }
    private fun changeLanguage(lang: String) {language.setLanguage(lang)}
    fun getString(arg: String): String = config.getProperty(arg)
    fun getDataPath() = config.getProperty("CollectionPath") + "collection.csv"
    fun setDataPath(path: String) {config["CollectionPath"] = path}
    fun getLogU() = config.getProperty("HistoryLogUnknown").toBoolean()
}