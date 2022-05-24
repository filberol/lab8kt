package lab6client.gui

import common.objects.Person
import lab6client.data.utilities.CollectionManager
import java.awt.*
import java.awt.event.*
import java.io.File
import javax.swing.BorderFactory
import javax.swing.ImageIcon
import javax.swing.JPanel
import kotlin.math.floor
import kotlin.math.sqrt
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

class TabGrapInfo(
    private val collection: CollectionManager
): JPanel(GridLayout()) {
    private val placeHolder = PlaceHolder()

    private var innerPanel = constructGraph()

    init { add(innerPanel) }

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
                    val fieldTable = reflectTableColumns(Person::class)
                    it.toolTipText = "<html>"
                    for (i in fieldTable.indices) {
                        it.toolTipText = it.toolTipText + "${fieldTable[i].split('/')[0]}: ${el[i]}<br>"
                    }
                    it.toolTipText = it.toolTipText + "</html>"
                })

            }
        }
    }

    private fun reflectTableColumns(element: KClass<out Any>): Array<String> {
        return element.memberProperties
            .sortedBy { it.name }
            .map { it.name }
            .filter { it != "action" }
            .toTypedArray()
    }

    private fun estimateWidthAndHeight(): Pair<Int, Int> {
        val size = collection.getSize()
        val sqrt = sqrt(collection.getSize().toDouble())
        if (size == 0) return Pair(width, height)
        return if (floor(sqrt) == sqrt) {
            Pair(
                width / sqrt.toInt(),
                height / sqrt.toInt())
        } else {
            Pair(
                width / (floor(sqrt).toInt() + 1),
                height / (floor(sqrt).toInt())
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
            Pair(floor(sqrt).toInt(), floor(sqrt).toInt() + 1)
        }
    }

    fun placeHolder() { add(placeHolder, BorderLayout.CENTER) }

    fun updateGraph() {
        remove(placeHolder)
        remove(innerPanel)
        innerPanel = constructGraph()

        isVisible = false
        add(innerPanel)
        isVisible = true
    }

    class PlaceHolder: JPanel() {
        private val imgSource = ImageIcon(File(
            "app/src/main/resources/images/load.png").absolutePath).image

        init {
            addComponentListener(object : ComponentAdapter() {
                override fun componentResized(e: ComponentEvent?) {
                    repaint()
                }
            })
        }

        override fun paint(g: Graphics?) {
            val g2d = g as Graphics2D
            g2d.drawImage(imgSource, 0, height/2, 50, 50, null)
        }

    }
}