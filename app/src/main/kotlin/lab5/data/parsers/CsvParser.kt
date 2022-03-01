package lab5.data.parsers

import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.FileInputStream

/**
 * Class, reads the collection from file separates by commas, sends arrays to construction.
 */
class CsvParser {
    @Throws(IOException::class)
    fun readCollection(path: String): ArrayList<Array<String>> {
        val collectionFile = File(path)
        val inputStream: InputStream = FileInputStream(collectionFile)
        val input = InputStreamReader(inputStream)
        val line = StringBuilder()
        var code: Int
        while (input.read().also { code = it } != -1) {
            line.append(code.toChar())
        }
        input.close()
        val lines = line.toString().split("\n").toTypedArray()
        val serializedCollection = ArrayList<Array<String>>()
        for (s in lines) {
            serializedCollection.add(s.trim().split(",").toTypedArray())
        }
        input.close()
        inputStream.close()
        return serializedCollection
    }
}