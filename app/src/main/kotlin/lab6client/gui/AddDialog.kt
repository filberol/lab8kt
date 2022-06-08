package lab6client.gui

import common.entities.User
import lab6client.data.commands.server.ServerAdd
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
class AddDialog(
    validator: FieldValidator,
    private val language: LanguageManager,
    private val builder: ObjectBuilder,
    private val user: User,
    private val collection: CollectionManager,
    private val connection: ConnectionHandler,
    private val screen: HomeFrame
): JFrame() {
    private val frameWidth = 700
    private val frameHeight = 800
    private val scSize: Dimension = Toolkit.getDefaultToolkit().screenSize

    private val dataPanel = PersonDataChooser(validator, language)
    private var degenerated: ArrayList<Any> = ArrayList()

    init {
        iconImage = ImageIcon(
            File("app/src/main/resources/images/icon.png")
                .absolutePath).image
        askFields()
    }

    private fun askFields() {
        val title = language.getString("Add")

        //Initializing Frame
        isAlwaysOnTop = true
        val dialog = JDialog(this, title,true)
        dialog.minimumSize = Dimension(700, 800)
        dialog.isResizable = true
        dialog.defaultCloseOperation = DISPOSE_ON_CLOSE
        dialog.setBounds(
            scSize.width/2 - frameWidth/2,
            scSize.height/2 - frameHeight/2,
            699, 799
        )
        dialog.pack()

        val allPanel = JPanel(BorderLayout())
        allPanel.border = BorderFactory.createEmptyBorder(10,5,5,10)
        allPanel.add(dataPanel, BorderLayout.CENTER)
        dialog.add(allPanel)

        //Buttons
        val buttPanel = JPanel()
        buttPanel.add(JButton(language.getString("Add")).also {
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

        dialog.pack()
        dialog.isVisible = true
    }

    private fun execute() {
        try {
            degenerated = dataPanel.getChosenValues()
            degenerated.add(0, 0)
            degenerated.add(4, ZonedDateTime.now())
            degenerated.add(12, user.getLogin())
            val obj = builder.buildObject(degenerated)
            ServerAdd(language, collection, connection).execute(obj)
        } catch (_: Exception) {}
        degenerated.clear()
        dispose()
        screen.updateCurrentTab()
    }
}