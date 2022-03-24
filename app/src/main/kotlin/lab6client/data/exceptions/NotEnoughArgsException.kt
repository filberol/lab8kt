package lab6client.data.exceptions

import lab6client.data.utilities.LanguageManager
import java.io.IOException

class NotEnoughArgsException(
    lang: LanguageManager
): IOException(
    lang getString "NotEnoughArgs"
)