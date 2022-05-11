package lab6client.gui

import common.entities.User
import lab6client.data.commands.server.ServerAdd
import lab6client.data.utilities.CollectionManager
import lab6client.data.utilities.LanguageManager
import lab6client.server.ConnectionHandler
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JPanel
import kotlin.system.exitProcess

class ButtonsMenu(
    private val language: LanguageManager,
    private val user: User,
    private val connection: ConnectionHandler,
    private val collection: CollectionManager,
    private val table: TabTable
): JPanel(
    GridLayout(5,1)
) {
    init {
        add(JPanel())

        add(JButton(language.getString("Re-login")).also {
            it.addActionListener {
                try {
                    RegDialog(user, language, connection)
                    Thread(connection).start()
                } catch (_: RuntimeException) {}
            }
        })

        add(JButton(language.getString("Refresh")).also {
            it.background = Color.GREEN
            it.addActionListener {
                println("refresh")
                ServerAdd(language, collection, connection).execute(null)
                table.updateTable()
                print("${user.getLogin()}>")
            }
        })

        add(JButton(language.getString("Exit")).also {
            it.background = Color.RED
            it.addActionListener {
                exitProcess(0)
            }
        })
    }
}