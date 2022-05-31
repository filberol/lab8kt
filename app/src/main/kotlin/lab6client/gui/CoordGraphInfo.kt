package lab6client.gui

import common.objects.Coordinates
import common.objects.Location
import lab6client.data.utilities.CollectionManager
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.JPanel

class CoordGraphInfo(
    private val collection: CollectionManager
): JPanel(null) {
    private var xGrid = 70
    private var yGrid = 50

    private var hPoint: Double = 1.0
    private var vPoint: Double = 1.0
    private val picWidth = (hPoint * 2).toInt()
    private val picHeight = (vPoint * 4).toInt()

    init {
        addMouseWheelListener {
            if (xGrid in 20..400) {
                xGrid += it.unitsToScroll
                yGrid += (it.unitsToScroll * (5.0/7.0)).toInt()
                repaint()
            }
            if (xGrid > 400) {
                xGrid = 400
                yGrid = (xGrid * (5.0/7.0)).toInt()
            }
            if (xGrid < 20) {
                xGrid = 21
                yGrid = (xGrid * (5.0/7.0)).toInt()
            }
        }
    }
    override fun paint(g: Graphics?) {
        val g2d = g as Graphics2D
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        hPoint = width.toDouble()/xGrid.toDouble()
        vPoint = height.toDouble()/yGrid.toDouble()

        g2d.color = Color.WHITE
        g2d.fillRect(0, 0, width, height)

        g2d.color = Color.LIGHT_GRAY
        drawHelpLines(g2d, 0, 200)
        drawHelpLines(g2d, 5, 68)
        if (xGrid >= 200) {
            for (i in 1..4) {
                val cord = xGrid/8.0 * i
                val offset = (cord * hPoint).toInt()
                val center = this.width/2
                g2d.drawString(cord.toInt().toString(), center + offset, height/2 + 10)
                g2d.drawString((-cord).toInt().toString(), center - offset, height/2 + 10)
            }
            for (j in 1..4) {
                val cord = yGrid/8.0 * j
                val offset = (cord * vPoint).toInt()
                val center = this.height/2
                g2d.drawString(cord.toInt().toString(), width/2, center + offset + 10)
                g2d.drawString((-cord).toInt().toString(), width/2, center - offset + 10)
            }
        }


        g2d.color = Color.BLACK
        g2d.drawLine(width/2, 0, width/2, height)
        g2d.drawLine(0, height/2, width, height/2)
        g2d.fillPolygon(intArrayOf(width/2-5, width/2, width/2+5), intArrayOf(8, 0, 8), 3)
        g2d.fillPolygon(intArrayOf(width-8, width, width-8), intArrayOf(height/2-5, height/2, height/2+5), 3)
        g2d.drawString("X", width-15, height/2 + 15)
        g2d.drawString("Y", width/2+5, 20)

        for (data in collection.getTableData()){
            val humanxPos = ((data[1] as Coordinates).getX() * hPoint).toInt() + this.width/2
            val humanyPos = ((data[1] as Coordinates).getY() * vPoint).toInt() + this.height/2
            val placexPos = ((data[7] as Location).getX() * hPoint).toInt() + this.width/2
            val placeyPos = ((data[7] as Location).getY() * vPoint).toInt() + this.height/2
            g2d.drawLine(humanxPos + picWidth/2, humanyPos + picHeight/2,
                placexPos + picWidth/2, placeyPos + picHeight/2)
            g2d.drawRoundRect(humanxPos, humanyPos, picWidth, picHeight,2,2)
            g2d.drawRect(placexPos, placeyPos, picWidth, picHeight)
        }
    }

    private fun drawHelpLines(g2d: Graphics2D, start: Int, xMax: Int) {
        if (xGrid > xMax) return
        for (i in start..xGrid step 10) {
            val cord = (i * hPoint).toInt()
            val center = this.width/2
            g2d.drawLine(center + cord, 0, center + cord, height)
            g2d.drawString(i.toString(), center + cord, height/2 + 10)
            g2d.drawLine(center - cord, 0, center - cord, height)
            g2d.drawString((-i).toString(), center - cord, height/2 + 10)
        }
        for (j in start..yGrid step 10) {
            val cord = (j * vPoint).toInt()
            val center = this.height/2
            g2d.drawLine(0, center + cord, width, center + cord)
            g2d.drawString(j.toString(), width/2, center + cord + 10)
            g2d.drawLine(0, center - cord, width, center - cord)
            g2d.drawString((-j).toString(), width/2, center - cord + 10)
        }
    }

    fun updateCoordinates() {
//        val hPoint = width/xGrid
//        val vPoint = height/yGrid
//        removeAll()
//        for (data in collection.getTableData()){
//            val comp = JButton(data[8].toString())
//            val xPos = ((data[1] as Coordinates).getX() * hPoint).toInt() + (hPoint * yGrid/2)
//            val yPos = ((data[1] as Coordinates).getY() * vPoint) + (vPoint * yGrid/2)
//            comp.setBounds(xPos, yPos, 30, 100)
//            comp.isVisible = true
//            add(comp)
//        }
    }
}