package lab6client.gui

import common.objects.Coordinates
import common.objects.Location
import common.objects.Person
import lab6client.data.utilities.CollectionManager
import lab6client.data.utilities.LanguageManager
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter
import java.awt.image.BufferedImage
import java.awt.image.RescaleOp
import java.io.File
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JPanel
import kotlin.math.abs


class TabCoordGraphInfo(
    private val collection: CollectionManager,
    private val language: LanguageManager
): JPanel(null) {
    //Default grid on screen
    private var xGrid = 70
    private var yGrid = 50

    //Scale of grid to actual size
    private var hPoint: Double = 1.0
    private var vPoint: Double = 1.0

    //Listening to mouse drag
    private var mousePt: Point = Point(0,0)
    //Offset from center of coordinates
    private var movX: Int = 0
    private var movY: Int = 0

    //Constant size of a picture
    private val picWidth = (hPoint * 15).toInt()
    private val picHeight = (vPoint * 30).toInt()

    private val stickMan = ImageIO.read(File(
        "app/src/main/resources/images/stickman.png"))
    private val house = ImageIcon(File(
        "app/src/main/resources/images/house.png").absolutePath).image

    private val rescaledImages = HashMap<String, BufferedImage>()

    init {
        cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        //Dragging function
        addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent) {
                mousePt = e.point
            }
        })
        //Listening and limiting the movement
        addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseDragged(e: MouseEvent) {
                val diffX = e.x - mousePt.x
                val diffY = e.y - mousePt.y
                movX += diffX
                if (abs(movX) > width/2 - 20) { movX -= diffX }
                movY += diffY
                if (abs(movY) > height/2 - 20) { movY -= diffY }
                mousePt = e.point
                repaint()
            }
        })
        //Scrolling function
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

        //Pixels to one point
        hPoint = width.toDouble()/xGrid.toDouble()
        vPoint = height.toDouble()/yGrid.toDouble()

        g2d.color = Color.WHITE
        g2d.fillRect(0, 0, width, height)

        //Drawing help lines if scale conforts
        g2d.color = Color.LIGHT_GRAY
        drawHelpLines(g2d, 0, 200)
        drawHelpLines(g2d, 5, 69)
        if (xGrid >= 200) { drawStaticNumbers(g2d) }

        //Drawin axis and labels
        g2d.color = Color.BLACK
        val halW = width/2
        val halH = height/2
        g2d.drawLine(halW + movX, 0, halW + movX, height)
        g2d.drawLine(0, halH + movY, width, halH + movY)
        g2d.fillPolygon(intArrayOf(halW-5 + movX, halW + movX, halW+5 + movX), intArrayOf(8, 0, 8), 3)
        g2d.fillPolygon(intArrayOf(width-8, width, width-8), intArrayOf(halH-5 + movY, halH + movY, halH+5 + movY), 3)
        g2d.drawString("X", width-15, halH + 15 + movY)
        g2d.drawString("Y", halW+5 + movX, 20)

        //Drawing elements and connections
        //if (!updated) { updateColorMap(); updated = true }
        for (data in collection.getTableData()){
            //Getting data from array
            val humanxPos = ((data[1] as Coordinates).getX() * hPoint).toInt() + this.width/2 + movX
            val humanyPos = ((data[1] as Coordinates).getY() * vPoint).toInt() + this.height/2 + movY
            val placexPos = ((data[7] as Location).getX() * hPoint).toInt() + this.width/2 + movX
            val placeyPos = ((data[7] as Location).getY() * vPoint).toInt() + this.height/2 + movY

            //Connection line
            g2d.drawLine(humanxPos + picWidth/2, humanyPos + picHeight/2,
                placexPos + picWidth/2, placeyPos + picWidth/2)
            //Human drawing
            g2d.drawImage(rescaledImages[data[9].toString()], humanxPos, humanyPos, picWidth, picHeight, null)
            if (xGrid < 200) { g2d.drawString(data[8].toString(), humanxPos, humanyPos - 7) }
            //Place drawing
            g2d.drawImage(house, placexPos, placeyPos, picWidth, picWidth, null)
            if (xGrid < 200) { g2d.drawString((data[7] as Location).getName(), placexPos, placeyPos - 7) }
        }
        updateTransParentButtons()
    }

    private fun getRandomOffset(): FloatArray {
        return floatArrayOf(
            (Math.random() * 200).toFloat().coerceAtLeast(50.0f),
            (Math.random() * 200).toFloat().coerceAtLeast(50.0f),
            (Math.random() * 200).toFloat().coerceAtLeast(50.0f),
        )
    }

    private fun drawHelpLines(g2d: Graphics2D, start: Int, xMax: Int) {
        if (xGrid > xMax) return
        for (i in start..xGrid step 10) {
            val cord = (i * hPoint).toInt()
            val center = this.width/2
            g2d.drawLine(center + cord + movX, 0, center + cord + movX, height)
            g2d.drawString(i.toString(), center + cord + movX, height/2 + 10 + movY)
            g2d.drawLine(center - cord + movX, 0, center - cord + movX, height)
            g2d.drawString((-i).toString(), center - cord + movX, height/2 + 10 + movY)
        }
        for (j in start..yGrid step 10) {
            if (j == 0) continue
            val cord = (j * vPoint).toInt()
            val center = this.height/2
            g2d.drawLine(0, center + cord + movY, width, center + cord + movY)
            g2d.drawString(j.toString(), width/2 + movX, center + cord + movY + 10)
            g2d.drawLine(0, center - cord + movY, width, center - cord + movY)
            g2d.drawString((-j).toString(), width/2 + movX, center - cord + movY + 10)
        }
    }

    private fun drawStaticNumbers(g2d: Graphics2D) {
        for (i in 1..4) {
            val cord = xGrid/8.0 * i
            val offset = (cord * hPoint).toInt()
            val center = this.width/2
            g2d.drawString(cord.toInt().toString(), center + offset + movX, height/2 + 10 + movY)
            g2d.drawString((-cord).toInt().toString(), center - offset + movX, height/2 + 10 + movY)
        }
        for (j in 1..4) {
            val cord = yGrid/8.0 * j
            val offset = (cord * vPoint).toInt()
            val center = this.height/2
            g2d.drawString(cord.toInt().toString(), width/2 + movX, center + offset + 10 + movY)
            g2d.drawString((-cord).toInt().toString(), width/2 + movX, center - offset + 10 + movY)
        }
    }

    //Each button lies on picture
    private fun updateTransParentButtons() {
        removeAll()
        for (data in collection.getTableData()){
            val comp = JPanel()
            val xPos = ((data[1] as Coordinates).getX() * hPoint).toInt() + width/2 + movX
            val yPos = ((data[1] as Coordinates).getY() * vPoint).toInt() + height/2 + movY
            comp.setBounds(xPos, yPos, picWidth, picHeight)
            val fieldTable = Reflector(language).reflectTableColumns(Person::class)
            comp.toolTipText = "<html>"
            for (i in fieldTable.indices) {
                comp.toolTipText = comp.toolTipText + "${fieldTable[i].split('/')[0]}: ${data[i]}<br>"
            }
            comp.toolTipText = comp.toolTipText + "</html>"
            add(comp)
        }
    }

    fun updateColorMap() {
        rescaledImages.clear()
        for (data in collection.getTableData()) {
            val owner = data[9].toString()
            if (!rescaledImages.containsKey(owner)) {
                rescaledImages[owner] = RescaleOp(floatArrayOf(0.0f, 0.0f, 0.0f), getRandomOffset(), null).filter(stickMan,null)
            }
        }
        repaint()
    }
}