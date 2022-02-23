package lab5.data.utilities

import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.util.Properties

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
        initialize()
    }
    private fun setDefaults() {
        config["Language"] = "en"
        config["Mode"] = "standalone"
        config["HistoryLength"] = "7"
        config["HistoryLog"] = "true"
        config["HistoryLogUnknown"] = "false"
    }
    private fun initialize() {
        if (config.contains("Language")) {
            language.setLanguage(config.getProperty("Language"))
        }
    }
    fun getString(arg: String): String {
        return config.getProperty(arg)
    }
}