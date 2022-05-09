package lab6client.gui.guides

import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.io.PipedOutputStream
import java.io.PipedInputStream
import java.io.PrintStream
import java.io.OutputStream
import java.io.IOException
import java.util.Scanner
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea

/*      Панелька позволяет поместить типичную консоль
    в кастомное поле и перенаправить потоки ввода
    и вывода в неё, не перелопачивая половину кода.
        Я не знаю, как реализовать русский язык(((
*/
fun main() {
    //Готовый пример для просмотра
    ConsoleFrame()
    //Консоль просто читает строки и пишет их в поле
    val scanner = Scanner(System.`in`)
    while (true) {
        val line = scanner.nextLine()
        println(line)
        if (line.equals("aboba")) {
            println("Ya hooch u pits i")
        }
    }
}
/*
   Удобно наследовать класс напрямую, так мы не вызываем
   никаких методов draw() и т. д.
 */
class ConsoleFrame: JFrame() {
    init {
        //Создаем простое окошко
        setBounds(100, 100, 400, 300)
        defaultCloseOperation = EXIT_ON_CLOSE
        isResizable = false
        val panel = JPanel()
        add(panel)

        /*
                Здесь потоки разделены на два поля для наглядности
            того, что получит в итоге scanner.nextLine().
            Если же поместить все потоки в одну JTextArea -
            то получите консоль.
                Её также можно натроить графически.
                Если вы захотите переделать ее в нормальную общую
            консоль, нужно просто провести обе операции в одном
            поле и читать команды как обычно.
         */
        val area1 = JScrollPane(JTextArea(5,30).also {
            it.wrapStyleWord = true

            /*
                Направляем ввод из текстового поля
                в стандартный поток ввода. Для этого
                используем кастомный PipedStream.
                см. ниже
             */
            val pipedStream = PipedOutputStream()
            System.setIn(PipedInputStream(pipedStream))
            it.addKeyListener(TestAreaInputListener(pipedStream))
        }).also {
            it.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
        }
        val area2 = JScrollPane(JTextArea(5, 30).also {
            it.wrapStyleWord = true

            /*
                Направляем стандартный вывод
                в текстовое поле. Для этого
                используем кастомный PipedStream.
                см. ниже
            */
            val outputStream = PrintStream(TestAreaOutputStream(it))
            System.setOut(outputStream)
            System.setErr(outputStream)

            //Стандартный метод уже выведет все куда нужно
            println("No Cheese in here!")
        }).also {
            it.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
        }

        //Теперь просто добавляем элементы на панель
        panel.add(JLabel("Input(separate) -> Write here + Enter"))
        panel.add(area1)
        panel.add(JLabel("Output(console)"))
        panel.add(area2)
        isVisible = true
    }



    /*  Класс потока, имплементирующий KeyListener позволяет
        одновременно использовать его как поток и заставить выполнять
        что-либо при каждом нажатии кнопки, поместив все это
        в один метод write()
     */
    class TestAreaInputListener(
        private val pipedStream: PipedOutputStream
    ): KeyListener {
        //Используем "буфер" для записи
        private var buffer: ArrayList<Char> = ArrayList()

        //Пустые методы интерфейса
        override fun keyPressed(e: KeyEvent?) {}
        override fun keyReleased(e: KeyEvent?) {}

        /*
            Особенность в том, что поток не является "динамическим"
            и не подразумевает стирание каких-либо символов.
            Лишь только для этого приходится дублировать символы в
            буфер и записывать в поток по ентеру
         */
        override fun keyTyped(e: KeyEvent?) {
            try {
                //Добавить или стереть символ из буфера
                if (e!!.keyChar != '') {
                    buffer.add(e.keyChar)
                } else {
                    if (buffer.size != 0) {
                        buffer.removeAt(buffer.size - 1)
                    }
                }
                //Типа flush()
                if (e.keyChar == '\n') {
                    pipedStream.write(buffer.map { it.code.toByte() }.toByteArray())
                    buffer.clear()
                }
            } catch (_: IOException) {}
        }
    }


    /*
        Более простой класс, перопределяющий вывод символов
        на добавление их к текстовому полю.
     */
    class TestAreaOutputStream(
        private val textArea: JTextArea
    ): OutputStream() {
        override fun write(b: Int) {
            //Добавляем
            textArea.append(b.toChar().toString())
            //Не забываем сместить курсор
            textArea.caretPosition = textArea.document.length
        }
    }
}