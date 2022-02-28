package lab5.data.utilities

import java.io.BufferedWriter
import java.io.PrintWriter

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