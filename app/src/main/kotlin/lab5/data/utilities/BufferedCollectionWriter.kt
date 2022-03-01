package lab5.data.utilities

import java.io.BufferedWriter
import java.io.PrintWriter

/**
 * Class, writing the collection to file.
 * Instead of CsvParser, writes only strings, the reason it is _smol_
 */
class BufferedCollectionWriter(
    path: String
) {
    private val writer = BufferedWriter(PrintWriter(path))

    fun writeCollection(serialized: ArrayList<String>) {
        for (line: String in serialized) {
            writer.write(line + "\n")
        }
        writer.close()
    }
}