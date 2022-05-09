package lab6client.gui

import common.entities.User
import lab6client.data.utilities.CollectionManager
import lab6client.data.utilities.LanguageManager
import lab6client.server.ConnectionHandler
import java.awt.Dimension
import java.io.File
import javax.swing.*


class HomeFrame(
    collection: CollectionManager,
    private val language: LanguageManager,
    private val user: User,
    private val connection: ConnectionHandler
): JFrame(language.getString("Title")) {

    private val tableManager = TabTable(collection)
    private val consoleTab = TabConsole().getScrollPanedConsole()
    private val tableTab = tableManager.getUpdatableTable()


    init {
        //Setting Main frame
        val imageIcon = ImageIcon(File("app/src/main/resources/images/icon.png")
            .absolutePath)
        iconImage = imageIcon.image
        minimumSize = Dimension(1100,760)
        //setBounds(50, 50, 1000, 650)
        defaultCloseOperation = EXIT_ON_CLOSE
        isResizable = true

        //Adding Panel with default layout
        val homePanel = JPanel()
        add(homePanel)

        //Adding Tabbed pane and adding tabs
        val tabbed = JTabbedPane(JTabbedPane.TOP)

        tabbed.addTab(language.getString("Console"), consoleTab)
        tabbed.addTab(language.getString("Table"), tableTab)
        tabbed.addTab("Graphic", JButton("ABOBA"))
        homePanel.add(tabbed)

        //Adding different buttons
        homePanel.add(JButton("Re-login").also {
            it.verticalAlignment = JButton.BOTTOM
            it.horizontalAlignment = JButton.LEFT
            it.addActionListener {
                RegDialog(user, language)
                connection.clearAttempts()
                Thread(connection).start()
            }
        })

        //homePanel.actionMap.put("switchTab", object : AbstractAction() {
        //    override fun actionPerformed(e: ActionEvent?) {
        //        if (tabbed.selectedIndex == tabbed.tabCount - 1) {
        //            tabbed.selectedIndex = 0
        //        } else {
        //            tabbed.selectedIndex = tabbed.selectedIndex
        //        }
        //    }
        //})
        //val inputMap = homePanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
        //inputMap.put(KeyStroke.getKeyStroke("shift released \u0009"), "switchTab")


        pack()
        isVisible = true
    }

    fun updateTableTab() {
        tableManager.updateTable()
    }
}
