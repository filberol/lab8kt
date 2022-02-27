package lab5.data.utilities

import java.io.BufferedWriter
import java.io.PrintWriter

class BufferedCollectionWriter(
    path: String
) {
    private val writer = BufferedWriter(PrintWriter(path))

    fun writeLine(line: String) {
        writer.write(line)
    }

    fun close() {
        writer.close()
    }
}