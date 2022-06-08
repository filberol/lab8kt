package lab6client.lang

import java.util.ListResourceBundle

class localization_en_US: ListResourceBundle() {
    private val properties: Array<Array<Any>> = arrayOf(
        arrayOf("Welcome", " Human collection ver."),

        arrayOf("EmptyCollection", " Collection is empty!"),

        arrayOf("FieldList", "Name,Coordinate X - Double,Coordinate Y - Integer ,Height - >0,Birthday/Example>2020-06-22,Eye Color/BLUE ORANGE BROWN,Hair Color/RED BLUE YELLOW ORANGE,Location Coordinate X - Float,Location Coordinate Y - Float,Location Name"),

        arrayOf("RegTitle", " Registration/Authorization"),
        arrayOf("Console", " Console"),
        arrayOf("Table", "Table"),
        arrayOf("Title", "Human collection"),
        arrayOf("LoginLabel", "|----<Login>----|"),
        arrayOf("PassLabel", "|--<Password>--|"),
        arrayOf("Re-login", "Re-Login"),
        arrayOf("Exit", "Exit"),
        arrayOf("Refresh", "Refresh"),
        arrayOf("Graphic", "Graphic Info"),
        arrayOf("Add", "Add"),
        arrayOf("Cancel", "Cancel"),
        arrayOf("OK", "OK"),
        arrayOf("AskId", "|-<Enter object ID>-|"),
        arrayOf("Remove", "Remove"),
        arrayOf("Wait", "Wait..."),
        arrayOf("Execute", "Type full path to file"),
        arrayOf("ButtEx", "Execute"),
        arrayOf("Update", "Update ID"),
        arrayOf("Birthday", "Birthday"),
        arrayOf("Coordinates", "Coordinates"),
        arrayOf("CreationDate", "Creation date"),
        arrayOf("EyeColor", "Eye color"),
        arrayOf("HairColor", "Hair color"),
        arrayOf("Height", "Height"),
        arrayOf("Id", "Id"),
        arrayOf("Location", "Location"),
        arrayOf("Name", "Name"),
        arrayOf("Owner", "Owner"),
        arrayOf("CoordinateInf", "Coordinate Info"),
        arrayOf("CFile", "Find..."),
        arrayOf("Blue", "Blue"),
        arrayOf("Orange", "Orange"),
        arrayOf("Brown", "Brown"),
        arrayOf("Red", "Red"),
        arrayOf("Yellow", "Yellow"),
        arrayOf("ChoTitle", "File script"),
        arrayOf("ChoFilt", "Text files (*.txt)"),
        arrayOf("LoginPassRule", "Login and password should be at least 4 symbols"),
        arrayOf("Login", "Login"),
        arrayOf("Register", "Register"),
        arrayOf("UseExi", "This login has already been taken."),
        arrayOf("WrLogin", "Login or password are incorrect."),
        arrayOf("WrPass", "Password is incorrect."),
        arrayOf("Fail", "Connection failed. Try again later."),
        arrayOf("SQlException", "Server DataBase error.")
    )
    override fun getContents(): Array<Array<Any>> {
        return properties
    }
}