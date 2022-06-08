package lab6client.gui

import common.objects.Person
import lab6client.data.utilities.CollectionManager
import lab6client.data.utilities.LanguageManager
import java.awt.BorderLayout
import java.awt.Color
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.util.regex.PatternSyntaxException
import javax.swing.*
import javax.swing.table.AbstractTableModel
import javax.swing.table.TableRowSorter
import kotlin.math.floor

class TabTable(
    private val collection: CollectionManager,
    private val language: LanguageManager
): JPanel(BorderLayout()) {
    private var pane = constructTable()
    private lateinit var table: JTable
    private val subPanel = JPanel()
    private lateinit var comboBox: JComboBox<String>

    private lateinit var tableModel: PersonTableModel
    private lateinit var smartSorter: PersonTableSorter

    private val textField = JTextField(40)

    init { add(constructSubPanel(), BorderLayout.SOUTH) }

    private fun constructTable(): JScrollPane {
        tableModel = PersonTableModel(collection, language)
        smartSorter = PersonTableSorter(tableModel, language)
        table = JTable(tableModel).also {
            it.rowSorter = smartSorter
            it.autoResizeMode = JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS
            resizeColumnWidth(it)
        }
        return JScrollPane(table
        ).also {
            it.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
            it.addComponentListener(object : ComponentAdapter() {
                override fun componentResized(e: ComponentEvent?) {
                    resizeColumnWidth(table)
                    repaint()
                }
            })
        }
    }



    private fun constructSubPanel(): JPanel {
        comboBox = JComboBox(Reflector(language).reflectTableColumns(Person::class))
        val filterButton = JButton(language.getString("OK"))
        var rowFilter: RowFilter<PersonTableModel, Int>? = null
        filterButton.addActionListener {
            try {
                rowFilter = RowFilter.regexFilter(textField.text, comboBox.selectedIndex)
            } catch (_: PatternSyntaxException) {}
            smartSorter.rowFilter = rowFilter
        }
        subPanel.apply {
            background = Color(0xC8DDF2)
            add(comboBox)
            add(textField)
            add(filterButton)
        }
        return subPanel
    }

    private fun resizeColumnWidth(table: JTable) {
        var allWidth = 0
        val columnModel = table.columnModel
        for (column in 0 until table.columnCount) {
            var width = 15
            for (row in 0 until table.rowCount) {
                val renderer = table.getCellRenderer(row, column)
                val comp = table.prepareRenderer(renderer, row, column)
                width = (comp.preferredSize.width + 1).coerceAtLeast(width)
            }
            if (width > 300) width = 300
            columnModel.getColumn(column).preferredWidth = width
            allWidth += width
        }
        val multi = this.width.toDouble() / allWidth.toDouble() - 0.01
        for (column in 0 until table.columnCount) {
            columnModel.getColumn(column).preferredWidth =
                floor((columnModel.getColumn(column).preferredWidth * multi)).toInt()
        }
    }

//    fun updateColumnLabels() {
//        val columnModel = table.columnModel
//        val columns = Reflector(language).reflectTableColumns(Person::class)
//        for (col in 0 until columnModel.columnCount) {
//            columnModel.getColumn(col).headerValue = columns[col]
//        }
//    }

    fun updateFilterBox() {
        subPanel.remove(comboBox)
        comboBox = JComboBox(Reflector(language).reflectTableColumns(Person::class))
        subPanel.add(comboBox, 0)
        repaint()
    }

    fun restoreFocus() {
        textField.requestFocus()
    }

    fun updateTable() {
        remove(pane)
        pane = constructTable()

        isVisible = false
        add(pane, BorderLayout.CENTER)
        isVisible = true
    }

    class PersonTableModel(
        collection: CollectionManager,
        language: LanguageManager
    ): AbstractTableModel() {
        private val smartColumns: Array<String> = Reflector(language)
            .reflectTableColumns(Person::class)
        private var data: Array<Array<Any>> = collection.getTableData()

        override fun getRowCount() = data.size
        override fun getColumnCount() = smartColumns.size
        override fun getValueAt(rowIndex: Int, columnIndex: Int) = data[rowIndex][columnIndex]
        override fun getColumnName(column: Int) = smartColumns[column]

        override fun isCellEditable(row: Int, column: Int): Boolean = false
    }

    class PersonTableSorter(
        model: PersonTableModel,
        language: LanguageManager
    ): TableRowSorter<PersonTableModel>(model) {
        private val columnClasses = Reflector(language)
            .columnClasses(Person::class)
        
        init {
            for (column in columnClasses.indices) {
                if (columnClasses[column] == Int::class.java) {
                    setComparator(6) { o1, o2 ->
                        (o1.toString().toInt()).compareTo(o2.toString().toInt()) }
                }
            }
        }
    }

}

