package lab6client.gui.guides

import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTable
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

fun main() {
    //Готовый пример для просмотра
    TableFrame()
}

/*
        Для создания таблиц в Swing по-хорошему нужны
    класс таблицы моделей, куча лисенеров для
    динамического обновления данной таблицы и
    интерактивного ввода данных в нее.
        Однако это слишком сложно. Здесь пример
    статической таблицы, которая при этом может
    обновляться по вызову метода, автоматически собирая
    свое содержание и описание столбцов.
        Реализовано засчет тотальной рефлексии
    всего что есть на свете.
 */
class TableFrame: JFrame() {
    //Создадим пару элементов коллекции с некоторыми примитивными и вложенными полями
    private val one = People("John", "Doe", "Curling", 2,
        false, GuideCoordinates(10,2))
    private val two = People("Mikhail", "Abobov", "Standing", 4,
        false, GuideCoordinates(15,8))
    private val three = People("Nick", "Man", "Running", 3,
        true, GuideCoordinates(12,20))

    //Элементы интерфейса очевидно НЕ ДОЛЖНЫ ИМЕТЬ ДОСТУП к самой коллекции,
    //но для примера поместим их здесь
    private val collection: ArrayList<People> = arrayListOf(one)

    //Удобно для каждого функционала выделить отдельный метод

    //Простой рефлексией достаем все поля нужного класса
    private val smartColumns: Array<String> = reflectTableColumns(People::class)
    //Превращаем коллекцию в сырые данные. См. метод
    private var data: Array<Array<Any>> = renewData(collection)
    //Билдим готовую таблицу. См. метод
    private var pane: JScrollPane = constructTable(data)

    //С панелькой будем работать, поэтому как отдельное поле
    private val panel: JPanel = JPanel()

    init {
        //Создаем простое окошко
        setBounds(100, 100, 800, 500)
        defaultCloseOperation = EXIT_ON_CLOSE
        isResizable = true
        add(panel)

        //Помещаем таблицу
        panel.add(pane)
        isVisible = true

        //Имитируем асинхронность
        Thread.sleep(4000)

        //Имитируем изменение коллекции
        collection.add(two)

        //Метод должен обновлять данные и подменять таблицу.
        //Вызываться сам или вручную
        updateTable()

        Thread.sleep(3000)
        collection.add(three)
        updateTable()

        /*
            Тупо подменять таблицу для каждого изменения
            коллекции не эффективный и не правильный способ,
            но все же самый простой для понимания и реализации
         */
    }

    //Это проще, чем писать все поля в таблице
    private fun reflectTableColumns(element: KClass<out Any>): Array<String> {
        return element.memberProperties
            //Сортируем по алфавиту, что бы не спутать колонки
            .sortedBy { it.name }
            //От класса поля переходим к его имени
            .map { it.name }
            //Приводим к съедобному массиву
            .toTypedArray()
    }

    //"Пересериализуем" данные
    //Этод метод можно запихнуть в менеджер
    private fun renewData(collection: ArrayList<People>): Array<Array<Any>> {
        //Мапим коллекцию, получаем вложенные массивы
        return collection.map { element ->
                element.javaClass.declaredFields
                //Внутри для каждого поля даем доступ
                .onEach { field ->  field.isAccessible = true }
                //Внутри сортируем по имени поля
                .sortedBy { it.name }
                //Внутри превращаем в значение
                .map { field -> field.get(element) }
                //Все приводим к съедобным массивам
                .toTypedArray()
            }.toTypedArray()
    }

    //Factory method для новго поля
    private fun constructTable(
        arrayData: Array<Array<Any>>
    ): JScrollPane {
        return JScrollPane(JTable(
            //Единственный простой конструктор
            arrayData, smartColumns
        ).also {
            it.autoResizeMode = JTable.AUTO_RESIZE_ALL_COLUMNS
        }).also {
            it.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
        }
    }

    //Как плавно подменить поле
    private fun updateTable() {
        //Убираем, поле не обновится
        panel.remove(pane)
        //Обновляем
        data = renewData(collection)
        pane = constructTable(data)

        //Поле обновится, только когда невидимо.
        //Обновляем только поле, не весь фрейм
        panel.isVisible = false
        panel.add(pane)
        panel.isVisible = true
    }
}

data class People(
    private val FirstName: String,
    private val LastName: String,
    private val Sport: String,
    private val Years: Int,
    private val Vegan: Boolean,
    private val Coordinates: GuideCoordinates
)

data class GuideCoordinates(
    private val x: Int,
    private val y: Int
) {
    //Вложенные поля выражаются как одна колонка
    //методом ниже.
    override fun toString(): String {
        return "$x / $y"
    }
}