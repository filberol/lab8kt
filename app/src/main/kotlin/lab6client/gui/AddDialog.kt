package lab6client.gui

import common.entities.User
import lab6client.data.commands.server.ServerAdd
import lab6client.data.exceptions.ParseException
import lab6client.data.utilities.*
import lab6client.server.ConnectionHandler
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.io.File
import java.time.ZonedDateTime
import javax.swing.*
import kotlin.reflect.KClass

class AddDialog(
    private val validator: FieldValidator,
    private val language: LanguageManager,
    private val builder: ObjectBuilder,
    private val user: User,
    private val collection: CollectionManager,
    private val connection: ConnectionHandler
): JFrame() {
    private val frameWidth = 600
    private val frameHeight = 400
    private val scSize: Dimension = Toolkit.getDefaultToolkit().screenSize
    private val textFont = Font("SansSerif", Font.ITALIC, 14)
    private val fieldFont = Font("Monospaced", Font.BOLD, 14)
    private val columns = 30

    private val fields: List<KClass<out Any>> = FieldContainer().getFieldTable()
    private val labels = language.getString("FieldList").split(",")

    private val degenerated: ArrayList<Any> = ArrayList()

    init {
        val imageIcon = ImageIcon(
            File("app/src/main/resources/images/icon.png")
            .absolutePath)
        iconImage = imageIcon.image
        askFields()
    }

    private fun askFields() {
        val title = language.getString("Add")

        //Initializing Frame
        isAlwaysOnTop = true
        val dialog = JDialog(this, title,true)
        dialog.minimumSize = Dimension(frameWidth+130, frameHeight+110)
        dialog.isResizable = true
        dialog.defaultCloseOperation = DISPOSE_ON_CLOSE
        dialog.setBounds(
            scSize.width/2 - frameWidth/2,
            scSize.height/2 - frameHeight/2,
            frameWidth, frameHeight
        )

        //Component placement
        val allPanel = JPanel(BorderLayout())
        allPanel.border = BorderFactory.createEmptyBorder(10,5,5,10)
        dialog.add(allPanel)

        //Labels
        val labelPanel = JPanel(GridLayout(0,1,10,10))
        for (i in fields.indices) {
            degenerated.add(i)
            labelPanel.add(JLabel(labels[i].replace('/', ' ')).also {
                it.font = textFont
                it.horizontalAlignment = SwingConstants.CENTER
            })

        }
        allPanel.add(labelPanel, BorderLayout.CENTER)

        //Fields
        val fieldPanel = JPanel(GridLayout(0,1,10,10))
        for (i in fields.indices) {
            fieldPanel.add(JTextField(columns).also {
                it.font = fieldFont
                it.addKeyListener(object : KeyAdapter() {
                    override fun keyTyped(e: KeyEvent?) {
                        try {
                            degenerated[i] = validator.parseField(
                                it.text + e!!.keyChar, fields[i])!!
                            it.foreground = Color.GREEN
                        } catch (e: ParseException) {
                            it.foreground = Color.RED
                        }
                    }
                })
            })
        }
        allPanel.add(fieldPanel, BorderLayout.EAST)

        //Buttons
        val buttPanel = JPanel()
        buttPanel.add(JButton(language.getString("Add")).also {
            it.addActionListener {
                fin()
                dispose()
            }
        })
        buttPanel.add(JButton(language.getString("Cancel")).also {
            it.addActionListener {
                dispose()
            }
        })
        allPanel.add(buttPanel, BorderLayout.SOUTH)

        //KeyStrokes Setting
        allPanel.actionMap.put("confirm", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent?) {
                fin()
            }
        })
        allPanel.actionMap.put("cancel", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent?) {
                dispose()
            }
        })
        val inputMap = allPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, ActionEvent.CTRL_MASK), "confirm")
        inputMap.put(KeyStroke.getKeyStroke('\n'), "confirm")
        inputMap.put(KeyStroke.getKeyStroke(''), "cancel")

        dialog.isVisible = true
    }

    private fun fin() {
        degenerated.add(0,0)
        degenerated.add(4, ZonedDateTime.now())
        degenerated.add(12, user.getLogin())
        try {
            ServerAdd(language, collection, connection).execute(builder.buildObject(degenerated))
        } catch (_: ClassCastException) {}
        degenerated.clear()
    }
}