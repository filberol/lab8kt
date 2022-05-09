package lab6client.gui

import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.io.IOException
import java.io.OutputStream
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.io.PrintStream
import javax.swing.JScrollPane
import javax.swing.JTextArea

/**
 * Basic Text Panel working as a console.
 */

class TabConsole {
    private val rows: Int = 30
    private val columns = 80

    fun getScrollPanedConsole(): JScrollPane {
        return JScrollPane(JTextArea(rows, columns).also {
            it.wrapStyleWord = true

            //Setting output to TextArea
            val outputStream = PrintStream(TextAreaOutputStream(it))
            System.setOut(outputStream)
            System.setErr(outputStream)

            //Setting input from TextArea
            val pipedStream = PipedOutputStream()
            System.setIn(PipedInputStream(pipedStream))
            it.addKeyListener(TextAreaInputListener(pipedStream))

        }).also {
            it.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
        }
    }

    /**
     * Class sending the System.'out' stream to the UI console
     */
    class TextAreaOutputStream(
        private val textArea: JTextArea
    ): OutputStream() {

        override fun write(b: Int) {
            textArea.append(b.toChar().toString())
            textArea.caretPosition = textArea.document.length
        }
    }

    class TextAreaInputListener(
        private val pipedStream: PipedOutputStream
    ): KeyListener {
        private var buffer: ArrayList<Char> = ArrayList()
        override fun keyPressed(e: KeyEvent?) {}
        override fun keyReleased(e: KeyEvent?) {}

        override fun keyTyped(e: KeyEvent?) {
            try {
                if (e!!.keyChar != '') {
                    buffer.add(e.keyChar)
                } else {
                    if (buffer.size != 0) {
                        buffer.removeAt(buffer.size - 1)
                    }
                }
                if (e.keyChar == ' ') {
                    buffer.add(e.keyChar)
                    buffer.add(e.keyChar)

                }
                if (e.keyChar == '\n') {
                    pipedStream.write(buffer.map {
                        it.code.toByte() }.toByteArray())
                    buffer.clear()
                }
            } catch (_: IOException) {}
        }
    }
}

