package lab6client.gui

import common.entities.User
import lab6client.data.utilities.CollectionManager
import lab6client.data.utilities.FieldValidator
import lab6client.data.utilities.LanguageManager
import lab6client.data.utilities.ObjectBuilder
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
    connection: ConnectionHandler,
    validator: FieldValidator,
    builder: ObjectBuilder
): JFrame(language.getString("Title")) {

    private val consoleTab = TabConsole().getScrollPanedConsole()

    private val tableManager = TabTable(collection)
    private val tableTab = tableManager.getUpdatableTable()

    private val graphManager = TabGrapInfo(collection)
    private val graphTab = graphManager.getUpdatableGraph()

    init {
        //Setting Main frame
        val imageIcon = ImageIcon(File("app/src/main/resources/images/icon.png")
            .absolutePath)
        iconImage = imageIcon.image
        minimumSize = Dimension(1100,720)
        preferredSize = Dimension(1100,720)
        //setBounds(50, 50, 1000, 650)
        defaultCloseOperation = EXIT_ON_CLOSE
        isResizable = true

        //Adding Panel with default layout
        val homePanel = JPanel(BorderLayout())
        homePanel.border = BorderFactory.createTitledBorder("@filberol")
        add(homePanel)

        //Adding Tabbed pane and adding tabs
        val tabbed = AutoUpdatableJTabbedPane(tableManager, graphManager)

        tabbed.addTab(language.getString("Table"), tableTab)
        tabbed.addTab(language.getString("Graphic"), graphTab)
        updateTableTab()
        homePanel.add(tabbed, BorderLayout.CENTER)

        //Adding different buttons
        homePanel.add(ButtonsMenu(
            language, user, connection, collection, tableManager, validator, builder
        ), BorderLayout.EAST)

        homePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK), "color")
        homePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK), "console")
        homePanel.actionMap.put("color", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent?) {
                tabbed.selectedIndex = (tabbed.selectedIndex + 1) % tabbed.tabCount
            }
        })
        homePanel.actionMap.put("console", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent?) {
                if (tabbed.indexOfComponent(consoleTab) == -1) {
                    tabbed.addTab(language.getString("Console"), consoleTab)
                    tabbed.selectedIndex = tabbed.tabCount - 1
                } else {
                    tabbed.remove(consoleTab)
                }
            }
        })


        pack()
        isVisible = true
    }

    fun updateTableTab() {
        tableManager.updateTable()
    }

    class AutoUpdatableJTabbedPane(
        private val tableManager: TabTable,
        private val graphManager: TabGrapInfo
    ): JTabbedPane() {
        override fun setSelectedIndex(index: Int) {
            when (tabCount - selectedIndex - 1) {
                0 -> tableManager.updateTable()
                1 -> graphManager.updateGraph()
            }
            super.setSelectedIndex(index)
        }
    }
}
