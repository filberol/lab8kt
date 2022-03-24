package lab6server.data.exceptions

import lab6server.data.utilities.LanguageManager

class FullCollectionException(
    lang: LanguageManager
): RuntimeException(
    lang getString "FullCollection"
)