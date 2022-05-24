package lab6client.gui

import common.entities.User
import lab6client.data.commands.server.ServerAdd
import lab6client.data.utilities.CollectionManager
import lab6client.data.utilities.FieldValidator
import lab6client.data.utilities.LanguageManager
import lab6client.data.utilities.ObjectBuilder
import lab6client.server.ConnectionHandler
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.File
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JPanel
import kotlin.system.exitProcess

class ButtonsMenu(
    private val language: LanguageManager,
    private val user: User,
    private val connection: ConnectionHandler,
    private val collection: CollectionManager,
    private val screen: HomeFrame,
    private val validator: FieldValidator,
    private val builder: ObjectBuilder
): JPanel(
    GridLayout(10,1)
) {
    private val reLoginButton = JButton().also {
        it.background = Color.LIGHT_GRAY
        it.addActionListener {
            try {
                RegDialog(user, language, connection)
                Thread(connection).start()
            } catch (_: RuntimeException) {}
        }
    }
    private val refreshButton = JButton().also { butt ->
        butt.background = Color.GREEN
        butt.addActionListener {
            isVisible = false
            (it.source as JButton).text = language.getString("Wait")
            isVisible = true
            val action = WaitForUpdate(language, collection, connection, screen, this)
            Thread(action).start()
        }
    }
    private val addButton = JButton().also {
        it.background = Color.MAGENTA
        it.addActionListener {
            AddDialog(validator, language, builder, user, collection, connection)
        }
    }
    private val removeButton = JButton().also {
        it.background = Color.ORANGE
        it.addActionListener {
            RemoveDialog(language, collection, connection)
        }
    }
    private val exitButton = JButton().also {
        it.background = Color.RED
        it.addActionListener {
            exitProcess(0)
        }
    }
    private val langs = arrayOf("Русский", "Russian", "English", "Norsk", "Lietuvių", "Español")
    private val langPackButton = LangComboBox(langs).also {
        it.selectedIndex = 0
        it.addActionListener(LangMenu(language, screen))
    }

    init {
        add(JPanel())
        add(JPanel())
        add(reLoginButton)
        add(refreshButton)
        add(addButton)
        add(removeButton)
        add(exitButton)
        add(langPackButton)
        updateLabels()
    }

    fun updateLabels() {
        reLoginButton.text = language.getString("Re-login")
        refreshButton.text = language.getString("Refresh")
        addButton.text = language.getString("Add")
        removeButton.text = language.getString("Remove")
        exitButton.text = language.getString("Exit")
        repaint()
    }

    class LangMenu(
        private val language: LanguageManager,
        private val screen: HomeFrame
    ): ActionListener {
        override fun actionPerformed(e: ActionEvent?) {
            val source = e!!.source as JComboBox<*>
            val locale = when(source.selectedItem?.toString()) {
                "Русский" -> "ru_RU"
                "Russian" -> "en_RU"
                "English" -> "en_US"
                "Norsk" -> "no_NO"
                "Lietuvių" -> "Lt_LT"
                "Español" -> "es_EQ"
                else -> "en_US"
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

    class WaitForUpdate(
        private val language: LanguageManager,
        private val collection: CollectionManager,
        private val connection: ConnectionHandler,
        private val screen: HomeFrame,
        private val buttons: ButtonsMenu
    ): Runnable {
        override fun run() {
            ServerAdd(language, collection, connection).execute(null)
            screen.updateCurrentTab()
            buttons.updateLabels()
        }
    }
}