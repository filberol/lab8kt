package lab6client.gui

import common.entities.User
import lab6client.data.utilities.LanguageManager
import lab6client.server.ConnectionHandler
import java.awt.*
import java.awt.event.*
import java.io.File
import java.util.*
import javax.swing.*
import kotlin.system.exitProcess

/**
 * Registration window. Provides fields, does not connect.
 */
class RegDialog(
    private val user: User,
    private val language: LanguageManager,
    private val connManager: ConnectionHandler,
    private val startup: Boolean = false
): JFrame() {
    private val frameWidth = 400
    private val frameHeight = 220
    private val scSize: Dimension = Toolkit.getDefaultToolkit().screenSize
    private val textFont = Font("SansSerif", Font.ITALIC, 14)
    private val fieldFont = Font("Monospaced", Font.BOLD, 14)
    private val columns = 37

    private lateinit var loginLabel: JLabel
    private lateinit var passLabel: JLabel
    private lateinit var loginButton: JButton
    private lateinit var registerButton: JButton
    private lateinit var cancButton: JButton
    private lateinit var dialog: CloseableJDialog
    private lateinit var status: JTextArea

    private var statusText = "..."

    init {
        title = language.getString("RegTitle")
        isAlwaysOnTop = true
        askLoginPass()
    }

    private fun askLoginPass() {
        //Dialog window
        dialog = CloseableJDialog(this, title, startup)
        dialog.isResizable = false
        dialog.defaultCloseOperation = JDialog.DISPOSE_ON_CLOSE
        dialog.setBounds(
            scSize.width/2 - frameWidth/2,
            scSize.height/2 - frameHeight/2,
            frameWidth, frameHeight
        )

        //Component placement
        val panel = JPanel()
        dialog.add(panel)

        //Login label
        loginLabel = JLabel(language.getString("LoginLabel")).also {
            it.font = textFont
            it.verticalAlignment = JLabel.TOP
            it.horizontalAlignment = JLabel.LEFT
        }
        panel.add(loginLabel)

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
        passLabel = JLabel(language.getString("PassLabel")).also {
            it.font = textFont
            it.verticalAlignment = JLabel.TOP
            it.horizontalAlignment = JLabel.LEFT
        }
        panel.add(passLabel)

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

        loginButton = JButton(language.getString("Login")).also {
            it.horizontalAlignment = JButton.LEFT
            it.addActionListener {
                setStatus("WaitPlease")
                connManager.resetRegister()
                checkAndConnect(loginField, passField)
            }
        }
        panel.add(loginButton)

        registerButton = JButton(language.getString("Register")).also {
            it.horizontalAlignment = JButton.LEFT
            it.addActionListener {
                setStatus("WaitPlease")
                connManager.setRegister()
                checkAndConnect(loginField, passField)
            }
        }
        panel.add(registerButton)

        //Cancel button
        cancButton = JButton(language.getString("Cancel")).also {
            it.horizontalAlignment = JButton.RIGHT
            it.addActionListener {
                dispose()
            }
        }
        panel.add(cancButton)

        val langs = arrayOf("Русский", "English", "Norsk", "Lietuvių", "Español")
        panel.add(LangComboBox(langs).also {
            it.selectedIndex = 0
            it.addActionListener(LangMenu(language, this))
        })

        status = JTextArea(statusText, 2, 28).also {
            it.font = textFont
            it.lineWrap = true
            it.wrapStyleWord = true
            it.isEditable = false
            it.isOpaque = false
        }
        panel.add(status)

        //KeyStrokes Setting
        panel.actionMap.put("confirm", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent?) {
                checkAndConnect(loginField, passField)
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

    private fun checkAndConnect(loginField: JTextField, passField: JPasswordField) {
        if (loginField.text.length >= 4 && passField.password.size >= 4) {
            user.readVars(loginField.text, String(passField.password))
            val res = connManager.tryToConnect()
            if (res == "GotColl") { dialog.anywayDispose() } else { setStatus(res) }
        } else {
            setStatus("LoginPassRule")
        }
        connManager.resetRegister()
    }

    private fun setStatus(value: String) {
        statusText = value
        status.text = language.getString(value)
        repaint()
    }

    private fun updateLabels() {
        dialog.title = language.getString("RegTitle")
        loginLabel.text = language.getString("LoginLabel")
        passLabel.text = language.getString("PassLabel")
        loginButton.text = language.getString("Login")
        registerButton.text = language.getString("Register")
        cancButton.text = language.getString("Cancel")
        status.text = language.getString(statusText)
        repaint()
    }

    class LangMenu(
        private val language: LanguageManager,
        private val screen: RegDialog
    ): ActionListener {
        override fun actionPerformed(e: ActionEvent?) {
            val source = e!!.source as JComboBox<*>
            val locale = when(source.selectedItem?.toString()) {
                "Русский" -> Locale("ru", "RU")
                "English" -> Locale("en", "US")
                "Norsk" -> Locale("no", "NO")
                "Lietuvių" -> Locale("Lt", "LT")
                "Español" -> Locale("es", "EQ")
                else -> Locale("en", "US")
            }
            language.setLanguage(locale)
            screen.updateLabels()
        }
    }

    class LangComboBox(langs: Array<String>): JComboBox<String>(langs) {
        override fun paint(g: Graphics?) {
            val g2d = g as Graphics2D
            g2d.drawImage(ImageIcon(
                File("app/src/main/resources/images/flag.png").absolutePath
            ).image, 0, 0, width, height, null)
        }
    }

    class CloseableJDialog(
        owner: JFrame,
        diTitle: String,
        private val startup: Boolean
    ): JDialog(owner, diTitle, true) {
        override fun dispose() {
            if (startup) exitProcess(0) else super.dispose()
        }
        fun anywayDispose() {
            super.dispose()
        }
    }
}