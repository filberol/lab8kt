package lab5.data.exceptions

import lab5.data.utilities.LanguageManager

class FullCollectionException(
    lang: LanguageManager
): RuntimeException(
    lang.getString("FullCollectionException")
)