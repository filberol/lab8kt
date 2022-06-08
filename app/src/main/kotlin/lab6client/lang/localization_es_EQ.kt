package lab6client.lang

import java.util.*

class localization_es_EQ: ListResourceBundle() {
    private val properties: Array<Array<Any>> = arrayOf(
        arrayOf("Welcome", " Colección humana ver."),

        arrayOf("EmptyCollection", " ¡La colección está vacía!"),

        arrayOf("FieldList", "Nombre, Coordenada X - Double, Coordenada Y - Integer, Altura - >0, Cumpleaños/Ejemplo>2020-06-22, Color de ojos/BLUE ORANGE BROWN, Color de cabello/RED BLUE YELLOW ORANGE, Ubicación Coordenada X - Float, Ubicación Coordenada Y - Float, Nombre de la ubicación"),

        arrayOf("RegTitle", " Registro/Autorización"),
        arrayOf("Console", " Consola"),
        arrayOf("Table", "Tabla"),
        arrayOf("Title", "Colección humana"),
        arrayOf("LoginLabel", "|----<Inicio de sesión>----|"),
        arrayOf("PassLabel", "|--<Contraseña>--|"),
        arrayOf("Re-login", " Volver a iniciar sesión"),
        arrayOf("Exit", "Salir"),
        arrayOf("Refresh", "Refrescar"),
        arrayOf("Graphic", "Información gráfica"),
        arrayOf("LoginRule", "El inicio de sesión debe tener al menos 4 símbolos"),
        arrayOf("PassRule", "La contraseña debe tener al menos 4 símbolos"),
        arrayOf("Add", "Agregar"),
        arrayOf("Cancel", "Cancelar"),
        arrayOf("OK", "OK"),
        arrayOf("AskId", "|-<Ingrese la ID del objeto>-|"),
        arrayOf("Remove", "Borrar"),
        arrayOf("Wait", "Esperar..."),
        arrayOf("Execute", "Escriba la ruta completa al archivo"),
        arrayOf("ButtEx", "Ejecutar"),
        arrayOf("Update", "Actualizar ID"),
        arrayOf("Birthday", "Cumpleaños"),
        arrayOf("Coordinates", "Coordenadas"),
        arrayOf("CreationDate", "Fecha de creación"),
        arrayOf("EyeColor", "Color de los ojos"),
        arrayOf("HairColor", "Color de pelo"),
        arrayOf("Height", "Altura"),
        arrayOf("Id", "Id"),
        arrayOf("Location", "Ubicación"),
        arrayOf("Name", "Nombre"),
        arrayOf("Owner", "Dueño(a)"),
        arrayOf("CoordinateInf", "Información de coordenadas"),
        arrayOf("CFile", "Encontrar..."),
        arrayOf("Blue", "Azul"),
        arrayOf("Orange", "Naranja"),
        arrayOf("Brown", "Marrón"),
        arrayOf("Red", "Rojo"),
        arrayOf("Yellow", "Amarillo"),
        arrayOf("ChoTitle", "Guión de archivo"),
        arrayOf("ChoFilt", "Archivos de texto (*.txt)"),
        arrayOf("LoginPassRule", "El nombre de usuario y la contraseña deben tener al menos 4 símbolos"),
        arrayOf("Login", "Acceso"),
        arrayOf("Register", "Registro"),
        arrayOf("UseExi", "Este inicio de sesión ya se ha realizado."),
        arrayOf("WrLogin", "Login or password are incorrect."),
        arrayOf("Fail", "El inicio de sesión o la contraseña son incorrectos."),
        arrayOf("SQlException", "Error de la base de datos del servidor.")
    )
    override fun getContents(): Array<Array<Any>> {
        return properties
    }
}