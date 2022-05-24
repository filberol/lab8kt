package lab6client.gui

import common.objects.Person
import lab6client.data.utilities.CollectionManager
import lab6client.data.utilities.LanguageManager
import java.awt.BorderLayout
import java.util.regex.PatternSyntaxException
import javax.swing.*
import javax.swing.table.AbstractTableModel
import javax.swing.table.TableRowSorter
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

class TabTable(
    private val collection: CollectionManager,
    private val language: LanguageManager
): JPanel(BorderLayout()) {
    private var pane = constructTable()

    private var tableModel = PersonTableModel(collection)
    private var smartSorter = PersonTableSorter(tableModel)

    init { add(constructSubPanel(), BorderLayout.SOUTH) }

    private fun constructTable(): JScrollPane {
        tableModel = PersonTableModel(collection)
        smartSorter = PersonTableSorter(tableModel)
        return JScrollPane(
            JTable(tableModel).also {
                it.rowSorter = smartSorter
            }
        ).also {
            it.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
        }
    }

    private fun constructSubPanel(): JPanel {
        var column = 0
        val subPanel = JPanel()
        val comboBox = JComboBox(Reflector.reflectTableColumns(Person::class)).also {
            it.addActionListener { e ->
                column = (e!!.source as JComboBox<*>).selectedIndex
            }
        }
        val textField = JTextField(40)
        val filterButton = JButton(language.getString("OK"))
        var rowFilter: RowFilter<PersonTableModel, Int>? = null
        filterButton.addActionListener {
            try {
                rowFilter = RowFilter.regexFilter(textField.text, column)
            } catch (_: PatternSyntaxException) {}
            smartSorter.rowFilter = rowFilter
        }
        subPanel.apply {
            add(comboBox)
            add(textField)
            add(filterButton)
        }
        return subPanel
    }

    fun updateTable() {
        remove(pane)
        pane = constructTable()

        isVisible = false
        add(pane, BorderLayout.CENTER)
        isVisible = true
    }

    class PersonTableModel(
        collection: CollectionManager
    ): AbstractTableModel() {
        private val rawElement = Person::class
        private val smartColumns: Array<String> = Reflector.reflectTableColumns(rawElement)
        private var data: Array<Array<Any>> = collection.getTableData()

        override fun getRowCount() = data.size
        override fun getColumnCount() = smartColumns.size
        override fun getValueAt(rowIndex: Int, columnIndex: Int) = data[rowIndex][columnIndex]
        override fun getColumnName(column: Int) = smartColumns[column]

        override fun isCellEditable(row: Int, column: Int): Boolean = false
    }

    class PersonTableSorter(
        model: PersonTableModel
    ): TableRowSorter<PersonTableModel>(model) {
        private val columnClasses = Reflector.columnClasses(Person::class)
        
        init {
            for (column in columnClasses.indices) {
                if (columnClasses[column] == Int::class.java) {
                    setComparator(6) { o1, o2 ->
                        (o1.toString().toInt()).compareTo(o2.toString().toInt()) }
                }
            }
        }
    }

    object Reflector {
        fun columnClasses(rawEl: KClass<out Any>): Array<Class<out Any>> {
            return rawEl.java.declaredFields
                .sortedBy { it.name }
                .filter { it.name != "action" }
                .map { it.type }
                .toTypedArray()
        }

        fun reflectTableColumns(rawEl: KClass<out Any>): Array<String> {
            return rawEl.memberProperties
                .sortedBy { it.name }
                .map { it.name }
                .filter { it != "action" }
                .toTypedArray()
        }
    }
}

