package lab6client.gui

import common.objects.EyeColor
import common.objects.HairColor
import lab6client.data.utilities.FieldContainer
import lab6client.data.utilities.LanguageManager
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.time.LocalDate
import java.time.format.DateTimeParseException
import javax.swing.*
import kotlin.reflect.KClass


class PersonDataChooser(
    language: LanguageManager
): JPanel(GridLayout(0, 2,10,10)) {
    //private val cont = FieldContainer()
    //private val fields = cont.getFieldTable()
    private val names = language.getString("FieldList")
        .split(",").map { it.split('/')[0] }

    private val textFont = Font("SansSerif", Font.ITALIC, 14)

    private val degenerated: ArrayList<Any> = ArrayList()
    private val collector = FieldBuilder(degenerated, language)

    init {
        for (el in names.indices) {
            degenerated.add(el)
            add(JLabel(language.getString(names[el])).also {
                it.font = textFont
                it.horizontalAlignment = SwingConstants.CENTER
            })
            add(
                when (el) {
                    0 -> collector.getStringChooser(el)
                    1 -> collector.getShortNumberChooser(el, Double::class, -100, 100)
                    2 -> collector.getShortNumberChooser(el, Int::class, -100, 100)
                    3 -> collector.getShortNumberChooser(el, Int::class, 60,250)
                    4 -> collector.getDatePicker(el)
                    5 -> collector.getEyeColorChooser(el)
                    6 -> collector.getHairColorChooser(el)
                    7 -> collector.getShortNumberChooser(el, Float::class, -100, 100)
                    8 -> collector.getShortNumberChooser(el, Float::class, -100, 100)
                    9 -> collector.getStringChooser(el)
                    else -> collector.getStringChooser(el)
                }
            )
        }
    }

    //fun getChosenValues() {}
    //fun setValues() {}

    class FieldBuilder(
        private val data: ArrayList<Any>,
        private val language: LanguageManager
    ) {
        private val years = (1990..2010).map { it.toString() }.toTypedArray()
        private val months = (1..12)
            .map { it.toString() }
            .map { if (it.length != 2) { "0$it" } else { it }}
            .toTypedArray()
        private val days = (1..31)
            .map { it.toString() }
            .map { if (it.length != 2) { "0$it" } else { it }}
            .toTypedArray()

        //Text Field
        fun getStringChooser(pos: Int): JTextField {
            return JTextField(30).also {
                it.addKeyListener(object : KeyAdapter() {
                    override fun keyTyped(e: KeyEvent?) {
                        data[pos] = it.text + e!!.keyChar
                    }
                })
                it.font = Font("Monospaced", Font.BOLD, 14)
            }
        }

        //Number Slider
        fun getShortNumberChooser(pos: Int, toWhat: KClass<out Any>, min: Int, max: Int): JSlider {
            return JSlider(min, max).also { slid ->
                slid.minorTickSpacing = 5
                slid.majorTickSpacing = 20
                slid.paintLabels = true
                slid.paintTicks = true
                slid.paintTrack = true
                slid.addChangeListener {
                    data[pos] = (it.source as JSlider).value to toWhat
                }
            }
        }

        //Date Picker
        fun getDatePicker(pos: Int): JPanel {
            val panel = JPanel()
            val yBox = JComboBox(years)
            val mBox = JComboBox(months)
            val dBox = JComboBox(days)
            val listener = PickActionListener(data, pos, yBox, mBox, dBox)
            yBox.addActionListener(listener)
            mBox.addActionListener(listener)
            dBox.addActionListener(listener)
            yBox.isEditable = true
            val prefSize = Dimension((yBox.preferredSize.width * 0.8604).toInt(), (yBox.preferredSize.height * 1.5).toInt())
            yBox.preferredSize = prefSize
            mBox.preferredSize = prefSize
            dBox.preferredSize = prefSize
            panel.apply {
                add(yBox)
                add(mBox)
                add(dBox)
            }
            return panel
        }
        class PickActionListener(
            private val data: ArrayList<Any>,
            private val pos: Int,
            private val yBox: JComboBox<*>,
            private val mBox: JComboBox<*>,
            private val dBox: JComboBox<*>,
        ): ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                try {
                    data[pos] = LocalDate.parse("${yBox.selectedItem}-${mBox.selectedItem}-${dBox.selectedItem}")
                    yBox.background = Color.GREEN
                } catch (ex: DateTimeParseException) {
                    (e!!.source as JComboBox<*>).background = Color.RED
                }
            }
        }


        //Enum Combo Box
        fun getEyeColorChooser(pos: Int): JComboBox<EyeColor> {
            val box = JComboBox(EyeColor.values())
            box.addActionListener {
                data[pos] = box.selectedItem!!
            }
            box.renderer = MyComboBoxRenderer(language)
            return box
        }
        fun getHairColorChooser(pos: Int): JComboBox<HairColor> {
            val box = JComboBox(HairColor.values())
            box.addActionListener {
                data[pos] = box.selectedItem!!
            }
            box.renderer = MyComboBoxRenderer(language)
            return box
        }
        class MyComboBoxRenderer(
            private val language: LanguageManager
        ) : JLabel(), ListCellRenderer<Any?> {
            init {
                isOpaque = true
                font = Font("Arial", Font.BOLD or Font.ITALIC, 14)
                background = Color.BLUE
                foreground = Color.YELLOW
            }

            override fun getListCellRendererComponent(
                list: JList<*>?, value: Any?,
                index: Int, isSelected: Boolean, cellHasFocus: Boolean
            ): Component {
                text = language.getString(value.toString())
                return this
            }
        }
    }
}