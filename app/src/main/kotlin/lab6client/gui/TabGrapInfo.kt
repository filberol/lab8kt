package lab6client.gui

import lab6client.data.utilities.CollectionManager
import java.awt.BorderLayout
import java.awt.GridLayout
import java.awt.event.*
import javax.swing.BorderFactory
import javax.swing.JPanel
import kotlin.math.floor
import kotlin.math.sqrt

class TabGrapInfo(
    private val collection: CollectionManager
) {
    private val panel = JPanel(GridLayout())
    private var innerPanel = constructGraph()

    init {
        panel.add(innerPanel)
    }

    fun getUpdatableGraph(): JPanel = panel

    private fun constructGraph(): JPanel {
        val grid = estimateGridLayout() ?: return JPanel()
        return JPanel(
            GridLayout(grid.first, grid.second)).also { it ->
            var estimated = estimateWidthAndHeight()
            for (el in collection.getTableData()) {
                val sprite = SpritePic(el, estimated.first, estimated.second)
                it.add(JPanel(BorderLayout()).also {
                    it.border = BorderFactory.createTitledBorder(el[8].toString())
                    it.add(sprite)
                    it.addComponentListener(object : ComponentAdapter() {
                        override fun componentResized(e: ComponentEvent?) {
                            estimated = estimateWidthAndHeight()
                            sprite.update(estimated.first, estimated.second)
                        }
                    })
                    it.addMouseListener(object : MouseAdapter() {
                        override fun mouseEntered(e: MouseEvent?) {
                            sprite.startAnimation()
                        }
                        override fun mouseExited(e: MouseEvent?) {
                            sprite.stopAnimation()
                        }
                    })
                })

            }
        }
    }

    private fun estimateWidthAndHeight(): Pair<Int, Int> {
        val size = collection.getSize()
        val sqrt = sqrt(collection.getSize().toDouble())
        if (size == 0) return Pair(panel.width, panel.height)
        return if (floor(sqrt) == sqrt) {
            Pair(
                panel.width / sqrt.toInt(),
                panel.height / sqrt.toInt())
        } else {
            Pair(
                panel.width / (size / 2 + 1),
                panel.height / (size / 2)
            )
        }
    }

    private fun estimateGridLayout(): Pair<Int, Int>? {
        val size = collection.getSize()
        val sqrt = sqrt(collection.getSize().toDouble())
        if (size == 0) return null
        return if (floor(sqrt) == sqrt) {
            Pair(sqrt.toInt(), sqrt.toInt())
        } else {
            Pair(size / 2, size / 2 + 1)
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