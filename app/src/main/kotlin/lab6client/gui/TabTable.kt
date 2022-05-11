package lab6client.gui

import common.objects.Person
import lab6client.data.utilities.CollectionManager
import java.awt.GridLayout
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTable
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

class TabTable(
    private val collection: CollectionManager
) {
    private val smartColumns: Array<String> = reflectTableColumns(Person::class)
    private var data: Array<Array<Any>> = collection.getTableData()
    private var pane: JScrollPane = constructTable(data)

    private val panel: JPanel = JPanel(GridLayout())

    fun getUpdatableTable(): JPanel {
        return panel
    }

    private fun reflectTableColumns(element: KClass<out Any>): Array<String> {
        return element.memberProperties
            .sortedBy { it.name }
            .map { it.name }
            .filter { it != "action" }
            .toTypedArray()
    }

    private fun constructTable(
        arrayData: Array<Array<Any>>
    ): JScrollPane {
        return JScrollPane(
            JTable(
            arrayData, smartColumns
        )
    ).also {
            it.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
        }
    }

    fun updateTable() {
        panel.remove(pane)
        data = collection.getTableData()
        pane = constructTable(data)

        panel.isVisible = false
        panel.add(pane)
        panel.isVisible = true
    }
}