package lab6client.gui

import common.entities.User
import lab6client.data.utilities.LanguageManager
import java.awt.Dimension
import java.awt.Font
import java.awt.Toolkit
import java.awt.event.*
import javax.swing.*
import kotlin.system.exitProcess

/**
 * Registration window. Provides fields, does not connect.
 */
class RegDialog(
    private val user: User,
    private val language: LanguageManager
): JFrame() {
    private val frameWidth = 300
    private val frameHeight = 180
    private val scSize: Dimension = Toolkit.getDefaultToolkit().screenSize
    private val textFont = Font("Verdana", Font.PLAIN, 16)
    private val fieldFont = Font("Verdana", Font.BOLD, 14)
    private val columns = 18

    init {
        askLoginPass()
    }

    private fun askLoginPass() {
        val title = language.getString("RegTitle")

        //Dialog window
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
        val inputMap = panel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
        inputMap.put(KeyStroke.getKeyStroke('\n'), "confirm")
        inputMap.put(KeyStroke.getKeyStroke(''), "cancel")

        dialog.isVisible = true
    }

    private fun checkAndRead(loginField: JTextField, passField: JPasswordField) {
        if (loginField.text.length < 4 || passField.password.size < 4) {
            //TODO
            exitProcess(0)
        } else {
            user.readVars(loginField.text, passField.password.toString())
            dispose()
        }

    }
}