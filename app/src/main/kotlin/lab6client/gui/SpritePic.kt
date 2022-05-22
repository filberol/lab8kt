package lab6client.gui

import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Image
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import java.io.File
import javax.swing.*

class SpritePic(
    data: Array<Any>,
    private var Pwidth: Int,
    private var Pheight: Int
): JPanel(), ActionListener {
    private var humPos = 0
    private var humSpeed = 3
    private var humHeight = 0.7
    private val timer = Timer(100,this)

    private val source = "app/src/main/resources/images/"
    val file = source + "back" + (Math.random()*7).toInt().toString() + ".png"
    private val randomSprite = ImageIcon(File(file).absolutePath).image

    private val randNum = (Math.random()*4).toInt()
    private val sprite1 = ImageIcon(File( source + "sprites/sprite" + randNum + "_1.png").absolutePath).image
    private val sprite2 = ImageIcon(File( source + "sprites/sprite" + randNum + "_2.png").absolutePath).image
    private val sprite3 = ImageIcon(File( source + "sprites/sprite" + randNum + "_3.png").absolutePath).image
    private val sprite4 = ImageIcon(File( source + "sprites/sprite" + randNum + "_4.png").absolutePath).image

    private var backwards = false

    private var hPos = 50
    private var hWidth = 50
    private var hHeight = 100

    init {
        border = BorderFactory.createTitledBorder(data[8].toString())
        humPos = (Math.random() * (Pwidth - Pwidth/3) + 10).toInt()
        humHeight = data[5].toString().toDouble()/250
        humSpeed = (Math.random() * 5 + 3).toInt()
        backwards = Math.random() >= 0.5
        if (backwards) humSpeed = -humSpeed
        hPos = (Pheight*(1-humHeight)).toInt()
        hWidth = Pwidth/6
        hHeight = (Pheight*humHeight).toInt()
        repaint()
    }

    override fun paint(g: Graphics?) {
        val g2d = g as Graphics2D
        g2d.drawImage(randomSprite,0,0, Pwidth, Pheight,null)
        g2d.drawImage(getHumanCordImage(), humPos, hPos,  hWidth, hHeight,null)
    }

    private fun getHumanCordImage(): Image {
        return when (backwards) {
            false -> { if (humPos % 20 >= 10) { sprite1 } else { sprite2 } }
            true -> { if (humPos % 20 >= 10) { sprite3 } else { sprite4 } }
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        humPos += humSpeed
        if (humPos >= Pwidth - Pwidth/4 || humPos <= 0) {
            humSpeed = - humSpeed
        }
        backwards = humSpeed <= 0
        repaint()
    }

    fun update(width: Int, height: Int) {
        hPos = (Pheight*(1-humHeight)).toInt()
        hWidth = Pwidth/6
        hHeight = (Pheight*humHeight).toInt()
        Pwidth = width
        Pheight = height
        repaint()
    }

    fun startAnimation() { timer.start() }
    fun stopAnimation() { timer.stop()}
}