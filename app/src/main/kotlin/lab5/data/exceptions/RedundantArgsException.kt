package lab5.data.exceptions

import lab5.data.utilities.LanguageManager
import java.io.IOException

class RedundantArgsException(
    lang: LanguageManager
): IOException(lang.getString("RedundantArgsException"))