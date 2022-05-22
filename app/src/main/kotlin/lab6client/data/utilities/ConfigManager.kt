package lab6client.data.utilities

import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.util.Properties

/**
 * Class stores the config for client, saved in file.
 * Supports common values.
 */
class ConfigManager(language: LanguageManager) {
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
        language.setLanguage(config.getProperty("Language"))
    }
    private fun setDefaults() {
        config["Language"] = "en_EN"
        config["Mode"] = "standalone"
        config["HistoryLength"] = "7"
        config["HistoryLog"] = "true"
        config["HistoryLogUnknown"] = "false"
        config["CollectionPath"] = "./app/src/main/resources/"
        config["InetAddress"] = "127.0.0.1"
        config["Port"] = "5428"
    }

    fun getString(arg: String): String = config.getProperty(arg)
    fun getLogU() = config.getProperty("HistoryLogUnknown").toBoolean()
    fun getAddress() = config.getProperty("InetAddress")!!
    fun getPort() = config.getProperty("Port").toInt()
}