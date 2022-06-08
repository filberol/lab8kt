package lab6client.gui

import common.entities.User
import lab6client.data.commands.server.ServerAdd
import lab6client.data.commands.server.ServerRemoveByID
import lab6client.data.utilities.*
import lab6client.server.ConnectionHandler
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.io.File
import java.time.ZonedDateTime
import javax.swing.*

/**
 * Class creating a registration window, connected
 * to user properties.
 * Invokes connection.
 */
class UpdateDialog(
    validator: FieldValidator,
    private val language: LanguageManager,
    private val builder: ObjectBuilder,
    private val user: User,
    private val collection: CollectionManager,
    private val connection: ConnectionHandler,
    private val screen: HomeFrame,
    private var toUpdate: Int = 0
): JFrame() {
    private val frameWidth = 600
    private val frameHeight = 500
    private val scSize: Dimension = Toolkit.getDefaultToolkit().screenSize
    private val textFont = Font("SansSerif", Font.ITALIC, 14)

    private val dataPanel = PersonDataChooser(validator, language)
    private var degenerated: ArrayList<Any> = ArrayList()

    init {
        iconImage = ImageIcon(
            File("app/src/main/resources/images/icon.png")
                .absolutePath).image
        askFields()
    }

    private fun askFields() {
        val title = language.getString("Update")

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

        val idPanel = JPanel(BorderLayout())
        idPanel.border = BorderFactory.createEmptyBorder(0,5,5,0)
        var idCombo = JComboBox(arrayOf(0))
        if (collection.getIds().size != 0) {
            idCombo = JComboBox(collection.getIdsOwnedBy(user.getLogin()).toTypedArray())
            toUpdate = idCombo.getItemAt(0)
        }
        idCombo.addActionListener {
            toUpdate = (it.source as JComboBox<*>).selectedItem as Int
        }
        idPanel.add(JLabel(language.getString("Id")).also {
            it.font = textFont
            it.horizontalAlignment = SwingConstants.CENTER
            it.border = BorderFactory.createEmptyBorder(0,20, 0, 20)
        }, BorderLayout.WEST)
        idPanel.add(idCombo, BorderLayout.CENTER)
        allPanel.add(idPanel, BorderLayout.NORTH)
        allPanel.add(dataPanel, BorderLayout.CENTER)

        //Buttons
        val buttPanel = JPanel()
        buttPanel.add(JButton(language.getString("Update")).also {
            it.addActionListener {
                execute()
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
                execute()
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

    private fun execute() {
        try {
            val id = toUpdate
            degenerated = dataPanel.getChosenValues()
            degenerated.add(0, 0)
            degenerated.add(4, ZonedDateTime.now())
            degenerated.add(12, user.getLogin())
            val obj = builder.buildObject(degenerated)
            ServerRemoveByID(language, collection, connection).execute(id)
            ServerAdd(language, collection, connection).execute(obj)
        } catch (_: Exception) {}
        degenerated.clear()
        dispose()
        screen.updateCurrentTab()
    }
}