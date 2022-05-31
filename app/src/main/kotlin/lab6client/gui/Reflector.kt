package lab6client.gui

import lab6client.data.utilities.LanguageManager
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

class Reflector(
        private val language: LanguageManager
    ) {
        fun columnClasses(rawEl: KClass<out Any>): Array<Class<out Any>> {
            return rawEl.java.declaredFields
                .sortedBy { it.name }
                .filter { it.name != "action" }
                .map { it.type }
                .toTypedArray()
        }

        fun reflectTableColumns(rawEl: KClass<out Any>): Array<String> {
            return rawEl.memberProperties
                .sortedBy { it.name }
                .map { it.name }
                .filter { it != "action" }
                .map { language.getString(it) }
                .toTypedArray()
        }
    }