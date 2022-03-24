package lab6server.data.exceptions

import lab6server.data.utilities.LanguageManager

class ExecuteException(
    lang: LanguageManager
): RuntimeException(
    lang getString "ExecuteException"
)