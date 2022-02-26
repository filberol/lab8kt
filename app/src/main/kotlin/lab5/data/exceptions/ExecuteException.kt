package lab5.data.exceptions

import lab5.data.utilities.LanguageManager

class ExecuteException(
    lang: LanguageManager
): RuntimeException(
    lang.getString(lang.getString("ExecuteException"))
)