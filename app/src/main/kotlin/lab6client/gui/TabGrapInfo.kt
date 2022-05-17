package lab6client.gui

import lab6client.data.utilities.CollectionManager
import java.awt.GridLayout
import java.io.File
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JPanel

class TabGrapInfo(
    private val collection: CollectionManager
) {
    private val sprite = ImageIcon(
        File("app/src/main/resources/images/ssprite.png")
        .absolutePath)
    private var innerPanel = constructGraph()
    private val panel = JPanel(GridLayout()).also {
        it.add(innerPanel)
    }

    fun getUpdatableGraph(): JPanel {
        return panel
    }

    private fun constructGraph(): JPanel {
        val size = collection.getSize()
        return JPanel(
            GridLayout(size/2, size/2+1)).also {
            it.add(JButton(sprite))
            it.add(JButton(sprite))
            it.add(JButton(sprite))
            it.add(JButton(sprite))
            it.add(JButton(sprite))

        }
    }

    fun updateGraph() {
        panel.remove(innerPanel)
        innerPanel = constructGraph()

        panel.isVisible = false
        panel.add(innerPanel)
        panel.isVisible = true
    }
}