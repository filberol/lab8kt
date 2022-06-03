package lab6client.gui

import lab6client.data.utilities.LanguageManager
import lab6client.run.Console
import lab6client.run.ScriptMode
import java.awt.*
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.io.File
import java.io.FileNotFoundException
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter

class ExecuteDialog(
    private val language: LanguageManager,
    private val console: Console
): JFrame() {
    private val frameWidth = 400
    private val frameHeight = 130
    private val scSize: Dimension = Toolkit.getDefaultToolkit().screenSize
    private val textFont = Font("SansSerif", Font.ITALIC, 14)
    private val fieldFont = Font("Monospaced", Font.BOLD, 14)

    private val label = JLabel(language.getString("Execute"))
    private val field = JTextField(40)
    private val button = JButton(language.getString("CFile"))
    private val okButton = JButton(language.getString("OK"))

    private val fileChooser = JFileChooser()

    init {
        iconImage = ImageIcon(
            File("app/src/main/resources/images/icon.png")
                .absolutePath).image
        title = language.getString("ButtEx")
        isResizable = false
        isAlwaysOnTop = true
        setBounds(
            scSize.width/2 - frameWidth/2,
            scSize.height/2 - frameHeight/2,
            frameWidth, frameHeight
        )

        layout = FlowLayout(FlowLayout.CENTER, 5, 5)

        label.font = textFont
        field.font = fieldFont
        field.addKeyListener(object : KeyAdapter() {
            override fun keyTyped(e: KeyEvent?) {
                field.foreground = Color.BLACK
            }
        })

        fileChooser.dialogTitle = language.getString("ChoTitle")
        fileChooser.fileFilter = FileNameExtensionFilter(language.getString("ChoFilt"), "txt")

        button.addActionListener { askPath() }
        okButton.addActionListener { execute() }

        add(label)
        add(field)
        add(button)
        add(okButton)

        isVisible = true
    }

    private fun askPath() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            field.text = fileChooser.selectedFile.absolutePath
        }

    }

    private fun execute() {
        try {
            ScriptMode(console, language).readFile(field.text)
            dispose()
        } catch (e: FileNotFoundException) {
            field.foreground = Color.RED
        }
    }
}