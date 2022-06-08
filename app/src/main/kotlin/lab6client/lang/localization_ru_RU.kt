package lab6client.lang

import java.util.*

class localization_ru_RU: ListResourceBundle() {
    private val properties: Array<Array<Any>> = arrayOf(
        arrayOf("Welcome", " Коллекция челов ver."),

        arrayOf("EmptyCollection", " Коллекция пуста!"),

        arrayOf("FieldList", "Имя,Координата X - Double,Координата Y - Integer ,Рост - >0,День рождения/Пример>2020-06-22,Цвет глаз/BLUE ORANGE BROWN,Цвет волос/RED BLUE YELLOW ORANGE,Координата места X - Float,Координата места Y - Float,Название места"),

        arrayOf("RegTitle", " Регистрация/Авторизация"),
        arrayOf("Console", "Консоль"),
        arrayOf("Table", "Таблица"),
        arrayOf("Title", "Коллекция челов"),
        arrayOf("LoginLabel", "|----<Логин>----|"),
        arrayOf("PassLabel", " |----<Пароль>---|"),
        arrayOf("Re-login", "Подключение"),
        arrayOf("Exit", "Выход"),
        arrayOf("Refresh", "Обновить"),
        arrayOf("Graphic", "Графическое представление"),
        arrayOf("LoginRule", "Логин должен содержать минимум 4 символа"),
        arrayOf("PassRule", "Пароль должен содержать минимум 4 символа"),
        arrayOf("Add", "Добавить"),
        arrayOf("Cancel", "Отмена"),
        arrayOf("OK", "OK"),
        arrayOf("AskId", "|-<Введите ID объекта>-|"),
        arrayOf("Remove", "Удалить"),
        arrayOf("Wait", "Подождите..."),
        arrayOf("Execute", "Выберите файл для исполнения"),
        arrayOf("ButtEx", "Выполнить"),
        arrayOf("Update", "Обновить ID"),
        arrayOf("Birthday", "Дата рождения"),
        arrayOf("Coordinates", "Координаты"),
        arrayOf("CreationDate", "Дата создания"),
        arrayOf("EyeColor", "Цвет глаз"),
        arrayOf("HairColor", "Цвет волос"),
        arrayOf("Height", "Высота"),
        arrayOf("Id", "Id"),
        arrayOf("Location", "Название места"),
        arrayOf("Name", "Имя"),
        arrayOf("Owner", "Владелец"),
        arrayOf("CoordinateInf", "Координатное представление"),
        arrayOf("CFile", "Обзор..."),
        arrayOf("Blue", "Голубой"),
        arrayOf("Orange", "Оранжевый"),
        arrayOf("Brown", "Коричневый"),
        arrayOf("Red", "Красный"),
        arrayOf("Yellow", "Желтый"),
        arrayOf("ChoTitle", "Выполнить"),
        arrayOf("ChoFilt", "Текстовые файлы (*.txt)"),
        arrayOf("LoginPassRule", "Логин и пароль должны содержать как минимум 4 символа."),
        arrayOf("Login", "Вход"),
        arrayOf("Register", "Регистрация"),
        arrayOf("UseExi", "Имя пользователя уже занято"),
        arrayOf("WrLogin", "Имя или пароль неправильны."),
        arrayOf("WrPass", "Неверный пароль."),
        arrayOf("Fail", "Ошибка подключения. Попробуйте позже"),
        arrayOf("SQlException", "Ошибка базы данных сервера.")
    )
    override fun getContents(): Array<Array<Any>> {
        return properties
    }
}