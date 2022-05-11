package lab6client.gui.guides

import java.awt.Color
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.*

fun main() {
    StrokeFrame()
}

class StrokeFrame: JFrame() {
    private val panel = JPanel()
    init {
        setBounds(100, 100, 800, 500)
        defaultCloseOperation = EXIT_ON_CLOSE
        isResizable = true
        add(panel)

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK), "color")
        panel.actionMap.put("color", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent?) {
                panel.background = Color.CYAN
            }
        })


        isVisible = true
    }
}