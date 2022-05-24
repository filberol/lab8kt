package lab6client.gui

import lab6client.data.commands.server.ServerRemoveByID
import lab6client.data.utilities.CollectionManager
import lab6client.data.utilities.LanguageManager
import lab6client.server.ConnectionHandler
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.io.File
import javax.swing.*

class RemoveDialog(
    private val language: LanguageManager,
    private val collection: CollectionManager,
    private val connection: ConnectionHandler
): JFrame() {
    private val frameWidth = 320
    private val frameHeight = 150
    private val scSize: Dimension = Toolkit.getDefaultToolkit().screenSize
    private val textFont = Font("SansSerif", Font.ITALIC, 14)
    private val fieldFont = Font("Monospaced", Font.BOLD, 14)
    private val columns = 30

    private var toRemove: Int = 0

    init {
        iconImage = ImageIcon(
            File("app/src/main/resources/images/icon.png")
                .absolutePath).image
        askId()
    }

    private fun askId() {
        val title = language.getString("Add")

        //Initializing Frame
        isAlwaysOnTop = true
        val dialog = JDialog(this, title,true)
        dialog.preferredSize = Dimension(frameWidth, frameHeight)
        dialog.isResizable = false
        dialog.defaultCloseOperation = DISPOSE_ON_CLOSE
        dialog.setBounds(
            scSize.width/2 - frameWidth/2,
            scSize.height/2 - frameHeight/2,
            frameWidth, frameHeight
        )

        val panel = JPanel(BorderLayout())
        panel.border = BorderFactory.createEmptyBorder(10,5,5,10)
        dialog.add(panel)

        val fieldPanel = JPanel()
        fieldPanel.add(JLabel(language.getString("AskId")).also {
            it.font = textFont
        })
        fieldPanel.add(JTextField(columns).also {
            it.font = fieldFont
            it.addKeyListener(object : KeyAdapter() {
                override fun keyTyped(e: KeyEvent?) {
                    try {
                        toRemove = (it.text + e!!.keyChar).toInt()
                        println("$toRemove - ${collection.contains(toRemove)}")
                        if (collection.contains(toRemove)) {
                            it.foreground = Color.GREEN
                        } else {
                            it.foreground = Color.RED
                        }
                    } catch (_: NullPointerException) {
                        it.foreground = Color.RED
                    } catch (_: NumberFormatException) {
                        it.foreground = Color.RED
                    }
                }
            })
        })
        panel.add(fieldPanel, BorderLayout.CENTER)

        val buttPanel = JPanel()
        buttPanel.add(JButton(language.getString("Remove")).also {
            it.addActionListener {
                execute()
            }
        })
        buttPanel.add(JButton(language.getString("Cancel")).also {
            it.addActionListener {
                dispose()
            }
        })
        panel.add(buttPanel, BorderLayout.SOUTH)

        panel.actionMap.put("confirm", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent?) {
                execute()
            }
        })
        panel.actionMap.put("cancel", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent?) {
                dispose()
            }
        })
        val inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, ActionEvent.CTRL_MASK), "confirm")
        inputMap.put(KeyStroke.getKeyStroke('\n'), "confirm")
        inputMap.put(KeyStroke.getKeyStroke(''), "cancel")

        dialog.isVisible = true
    }

    private fun execute() {
        if (collection.contains(toRemove)) {
            ServerRemoveByID(language, collection, connection).execute(toRemove)
        }
    }
}