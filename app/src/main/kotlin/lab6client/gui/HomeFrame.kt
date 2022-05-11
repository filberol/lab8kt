package lab6client.gui

import common.entities.User
import lab6client.data.utilities.CollectionManager
import lab6client.data.utilities.LanguageManager
import lab6client.server.ConnectionHandler
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.io.File
import javax.swing.*


class HomeFrame(
    collection: CollectionManager,
    language: LanguageManager,
    user: User,
    connection: ConnectionHandler
): JFrame(language.getString("Title")) {

    private val consoleTab = TabConsole().getScrollPanedConsole()
    private val tableManager = TabTable(collection)
    private val tableTab = tableManager.getUpdatableTable()


    init {
        //Setting Main frame
        val imageIcon = ImageIcon(File("app/src/main/resources/images/icon.png")
            .absolutePath)
        iconImage = imageIcon.image
        minimumSize = Dimension(1100,720)
        //setBounds(50, 50, 1000, 650)
        defaultCloseOperation = EXIT_ON_CLOSE
        isResizable = true

        //Adding Panel with default layout
        val homePanel = JPanel(BorderLayout())
        homePanel.border = BorderFactory.createTitledBorder("@filberol")
        add(homePanel)

        //Adding Tabbed pane and adding tabs
        val tabbed = AutoUpdatableJTabbedPane(tableManager)

        tabbed.addTab(language.getString("Console"), consoleTab)
        tabbed.addTab(language.getString("Table"), tableTab)
        tabbed.addTab(language.getString("Graphic"), JButton("ABOBA"))
        homePanel.add(tabbed, BorderLayout.CENTER)

        //Adding different buttons
        homePanel.add(ButtonsMenu(
            language, user, connection, collection, tableManager
        ), BorderLayout.EAST)

        homePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK), "color")
        homePanel.actionMap.put("color", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent?) {
                tabbed.selectedIndex = (tabbed.selectedIndex + 1) % 3
            }
        })


        pack()
        isVisible = true
    }

    fun updateTableTab() {
        tableManager.updateTable()
    }

    class AutoUpdatableJTabbedPane(
        private val tableManager: TabTable
    ): JTabbedPane() {
        override fun setSelectedIndex(index: Int) {
            if (selectedIndex == 0) tableManager.updateTable()
            super.setSelectedIndex(index)
        }
    }
}
