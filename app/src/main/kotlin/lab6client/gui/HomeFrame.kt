package lab6client.gui

import common.entities.User
import lab6client.data.utilities.CollectionManager
import lab6client.data.utilities.FieldValidator
import lab6client.data.utilities.LanguageManager
import lab6client.data.utilities.ObjectBuilder
import lab6client.run.Console
import lab6client.server.ConnectionHandler
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.io.File
import javax.swing.*


class HomeFrame(
    collection: CollectionManager,
    private val language: LanguageManager,
    user: User,
    connection: ConnectionHandler,
    validator: FieldValidator,
    builder: ObjectBuilder
): JFrame(language.getString("Title")) {
    private var console: Console? = null
    private val consoleTab = TabConsole().getScrollPanedConsole()

    private val tableManagerPanel = TabTable(collection, language)
    private val graphManagerPanel = TabGrapInfo(collection, language)
    private val coordGraphInfo = CoordGraphInfo(collection)

    //Adding Tabbed pane and adding tabs
    private val tabbed = CustJTabbedPane(graphManagerPanel, coordGraphInfo)

    private val buttonMenu = ButtonsMenu(
        language, user, connection, collection, this, validator, builder
    )

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

        tabbed.addTab(language.getString("Table"), tableManagerPanel)
        tabbed.addTab(language.getString("Graphic"), graphManagerPanel)
        tabbed.addTab(language.getString("CoordinateInf"), coordGraphInfo)
        tableManagerPanel.updateTable()
        homePanel.add(tabbed, BorderLayout.CENTER)

        //Adding different buttons
        homePanel.add(buttonMenu, BorderLayout.EAST)

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

    fun updateTableTab() { tableManagerPanel.updateTable() }

    fun updateCurrentTab() {
        when (tabbed.selectedIndex) {
            0 -> tableManagerPanel.updateTable()
            1 -> graphManagerPanel.updateGraph()
            2 -> coordGraphInfo.updateCoordinates()
        }
    }

    fun updateLabels() {
        title = language.getString("Title")
        tabbed.setTitleAt(0, language.getString("Table"))
        tabbed.setTitleAt(1, language.getString("Graphic"))
        tabbed.setTitleAt(2, language.getString("CoordinateInf"))
        if (tabbed.tabCount > 3) {
            tabbed.setTitleAt(3, language.getString("Console"))
        }
        buttonMenu.updateLabels()
        tableManagerPanel.updateColumnLabels()
        tableManagerPanel.updateFilterBox()
        repaint()
    }

    fun setConsole(con: Console) {
        console = con
    }

    fun getConsole() = console!!

    class CustJTabbedPane(
        private val graph: TabGrapInfo,
        private val coord: CoordGraphInfo
    ): JTabbedPane() {
        private var graphIsInit = false
        private var coordIsInit = false
        override fun setSelectedIndex(index: Int) {
            super.setSelectedIndex(index)
            when (index) {
                1 -> if (!graphIsInit) {graph.updateGraph(); graphIsInit = true}
                2 -> if (!coordIsInit) {coord.updateCoordinates(); coordIsInit = true}
            }
        }
    }
}
