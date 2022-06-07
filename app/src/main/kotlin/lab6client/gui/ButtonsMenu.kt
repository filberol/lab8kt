package lab6client.gui

import common.entities.User
import lab6client.data.commands.server.ServerAdd
import lab6client.data.utilities.CollectionManager
import lab6client.data.utilities.FieldValidator
import lab6client.data.utilities.LanguageManager
import lab6client.data.utilities.ObjectBuilder
import lab6client.server.ConnectionHandler
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.Locale
import javax.swing.*
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
    GridLayout(13,1)
) {
    private val textFont = Font("SansSerif", Font.ITALIC, 14)

    private val userLabel = JLabel(user.getLogin()).also {
        it.font = textFont
        it.horizontalAlignment = JLabel.CENTER
        it.border = BorderFactory.createLineBorder(Color.GRAY, 8)

    }
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
    private val updateButton = JButton().also {
        it.background = Color.CYAN
        it.addActionListener {
            UpdateDialog(validator, language, builder, user, collection, connection)
        }
    }
    private val executeButton = JButton().also {
        it.background = Color.BLUE
        it.addActionListener {
            ExecuteDialog(language, screen.getConsole())
        }
    }
    private val exitButton = JButton().also {
        it.background = Color.RED
        it.addActionListener {
            exitProcess(0)
        }
    }
    private val langs = arrayOf("Русский", "English", "Norsk", "Lietuvių", "Español")
    private val langPackButton = JComboBox(langs).also {
        it.selectedIndex = langs.indexOf(language.getLocaleName())
        it.addActionListener(LangMenu(language, screen))
    }

    init {
        add(JPanel())
        add(JPanel())
        add(userLabel)
        add(reLoginButton)
        add(refreshButton)
        add(addButton)
        add(removeButton)
        add(updateButton)
        add(executeButton)
        add(exitButton)
        add(JPanel())
        add(JPanel())
        add(langPackButton)
        updateLabels()
    }

    fun updateLabels() {
        reLoginButton.text = language.getString("Re-login")
        refreshButton.text = language.getString("Refresh")
        addButton.text = language.getString("Add")
        removeButton.text = language.getString("Remove")
        updateButton.text = language.getString("Update")
        executeButton.text = language.getString("ButtEx")
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

//    class LangComboBox(langs: Array<String>): JComboBox<String>(langs) {
//        override fun paint(g: Graphics?) {
//            val g2d = g as Graphics2D
//            g2d.drawImage(ImageIcon(
//                File("app/src/main/resources/images/flag.png").absolutePath
//            ).image, 3, 3, width-6, height-6, null)
//        }
//    }

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

//    class LanguageComboBoxRenderer(): JLabel(), ListCellRenderer<Any> {
//
//        init {
//            isOpaque = true
//            horizontalAlignment = CENTER
//            verticalAlignment = CENTER
//        }

//        override fun getListCellRendererComponent(
//            list: JList<out Any>?,
//            value: Any?,
//            index: Int,
//            isSelected: Boolean,
//            cellHasFocus: Boolean
//        ): Component {
//            val selectedIndex = value as Int
//            if (list != null) {
//                if (isSelected) {
//                    background = list.selectionBackground
//                    foreground = list.selectionForeground
//                } else {
//                    background = list.background
//                    foreground = list.foreground
//                }
//            }
//
//                //val langImage = ImageIcon(File)
//        }
//
//    }
}