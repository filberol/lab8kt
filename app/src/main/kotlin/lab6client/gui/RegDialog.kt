package lab6client.gui

import common.entities.User
import lab6client.data.utilities.LanguageManager
import lab6client.server.ConnectionHandler
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Toolkit
import java.awt.event.*
import javax.swing.*

/**
 * Registration window. Provides fields, does not connect.
 */
class RegDialog(
    private val user: User,
    private val language: LanguageManager,
    private val connManager: ConnectionHandler
): JFrame() {
    private val frameWidth = 300
    private val frameHeight = 180
    private val scSize: Dimension = Toolkit.getDefaultToolkit().screenSize
    private val textFont = Font("SansSerif", Font.ITALIC, 14)
    private val fieldFont = Font("Monospaced", Font.BOLD, 14)
    private val columns = 30

    init {
        askLoginPass()
    }

    private fun askLoginPass() {
        if (connManager.isProcessing()) {
            throw RuntimeException()
        }
        if (connManager.isConnected()) {
            println("reconnect")
        }
        val title = language.getString("RegTitle")

        //Dialog window
        isAlwaysOnTop = true
        val dialog = JDialog(this, title, true)
        dialog.isResizable = false
        dialog.defaultCloseOperation = DISPOSE_ON_CLOSE
        dialog.setBounds(
            scSize.width/2 - frameWidth/2,
            scSize.height/2 - frameHeight/2,
            frameWidth, frameHeight
        )

        //Component placement
        val panel = JPanel()
        dialog.add(panel)

        //Login label
        panel.add(JLabel(language.getString("LoginLabel")).also {
            it.font = textFont
            it.verticalAlignment = JLabel.TOP
            it.horizontalAlignment = JLabel.LEFT
        })

        //Login Field
        val loginField = JTextField(columns).also {
            it.horizontalAlignment = JTextField.LEFT
            it.font = fieldFont
        }
        loginField.addKeyListener(object : KeyAdapter() {
            override fun keyTyped(e: KeyEvent?) {
                if (e!!.keyChar == '\b') {
                    loginField.foreground = Color.RED
                    return
                }
                if (loginField.text.trim().length < 3) {
                    loginField.foreground = Color.RED
                    loginField.toolTipText = language.getString("LoginRule")
                } else {
                    loginField.foreground = Color.GREEN
                    loginField.toolTipText = null
                }
            }
        })
        panel.add(loginField)

        //Password label
        panel.add(JLabel(language.getString("PassLabel")).also {
            it.font = textFont
            it.verticalAlignment = JLabel.TOP
            it.horizontalAlignment = JLabel.LEFT
        })

        //Password Field
        val passField = JPasswordField(columns).also {
            it.horizontalAlignment = JTextField.LEFT
            it.font = fieldFont
        }
        passField.addKeyListener(object : KeyAdapter() {
            override fun keyTyped(e: KeyEvent?) {
                if (e!!.keyChar == '\b') {
                    passField.foreground = Color.RED
                    return
                }
                if (passField.password.size < 3) {
                    passField.foreground = Color.RED
                    passField.toolTipText = language.getString("PassRule")
                } else {
                    passField.foreground = Color.GREEN
                    passField.toolTipText = null
                }
            }
        })
        panel.add(passField)

        //Confirmation button
        panel.add(JButton("OK").also {
            it.horizontalAlignment = JButton.LEFT
            it.addActionListener {
                checkAndRead(loginField, passField)
            }
        })

        //Cancel button
        panel.add(JButton("Cancel").also {
            it.horizontalAlignment = JButton.RIGHT
            it.addActionListener {
                dispose()
            }
        })

        //KeyStrokes Setting
        panel.actionMap.put("confirm", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent?) {
                checkAndRead(loginField, passField)
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

    private fun checkAndRead(loginField: JTextField, passField: JPasswordField) {
        if (loginField.text.length >= 4 && passField.password.size >= 4) {
            user.readVars(loginField.text, passField.password.toString())
            dispose()
        }

    }
}