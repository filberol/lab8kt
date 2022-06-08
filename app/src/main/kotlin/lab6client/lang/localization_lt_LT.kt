package lab6client.lang

import java.util.*

class localization_lt_LT: ListResourceBundle() {
    private val properties: Array<Array<Any>> = arrayOf(
        arrayOf("Welcome", " Žmonių kolekcija ver."),

        arrayOf("EmptyCollection", " Kolekcija tuščia!"),

        arrayOf("FieldList", "Vardas, Koordinatė X – Double, Koordinatė Y – Integer,Augtis – >0,Gimtadienis/Pavyzdys>2020-06-22,Akių spalva/BLUE ORANGE BROWN,Plaukų spalva/RED BLUE YELLOW ORANGE,Vietos koordinatė X – Float, Vietos koordinatė Y – Float, vietos pavadinimas"),

        arrayOf("RegTitle", " Registracija / įgaliojimas"),
        arrayOf("Console ", " konsolė"),
        arrayOf("Table", "Lentelė"),
        arrayOf("Title", "Žmonių kolekcija"),
        arrayOf("LoginLabel", "|----<Prisijungti>----|"),
        arrayOf("PassLabel", "|--<Slaptažodis>--|"),
        arrayOf("Re-login", "Pakartotinis"),
        arrayOf("Exit", "Išeiti"),
        arrayOf("Refresh", "Atnaujinti"),
        arrayOf("Graphic", "Grafinė informacija"),
        arrayOf("LoginRule", "Prisijungimą turi sudaryti bent 4 simboliai"),
        arrayOf("PassRule", "Slaptažodį turi sudaryti bent 4 simboliai"),
        arrayOf("Add", "Pridėti"),
        arrayOf("Cancel", "Atšaukti"),
        arrayOf("OK", "Gerai"),
        arrayOf("AskId", "|-<Įveskite objekto ID>-|"),
        arrayOf("Remove", "Ištrinti"),
        arrayOf("Wait", "Laukti..."),
        arrayOf("Execute", "Įveskite visą failo kelią"),
        arrayOf("ButtEx", "Vykdyti"),
        arrayOf("Update", "Atnaujinti ID"),
        arrayOf("Birthday", "Gimtadienis"),
        arrayOf("Coordinates", "Koordinatės"),
        arrayOf("CreationDate", "Sukūrimo data"),
        arrayOf("EyeColor", "Akių spalva"),
        arrayOf("HairColor", "Plaukų spalva"),
        arrayOf("Height", "Aukštis"),
        arrayOf("Id", "Id"),
        arrayOf("Location", "Vieta"),
        arrayOf("Name", "Vardas"),
        arrayOf("Owner", "Savininkas"),
        arrayOf("CoordinateInf", "Koordinatės informacija"),
        arrayOf("CFile", "Rasti..."),
        arrayOf("Blue", "Mėlyna"),
        arrayOf("Orange", "Orange"),
        arrayOf("Brown", "Oranžinė"),
        arrayOf("Red", "Raudona"),
        arrayOf("Yellow", "Geltona"),
        arrayOf("ChoTitle", "Failo scenarijus"),
        arrayOf("ChoFilt", "Tekstiniai failai (*.txt)"),
        arrayOf("LoginPassRule", "Prisijungimo vardą ir slaptažodį turi sudaryti bent 4 simboliai"),
        arrayOf("Login", "Prisijungti"),
        arrayOf("Register", "Registruotis"),
        arrayOf("UseExi", "Šis prisijungimas jau priimtas."),
        arrayOf("WrLogin", "Neteisingas prisijungimo vardas arba slaptažodis."),
        arrayOf("Fail", "Nepavyko prisijungti. Pabandyti dar kartą vėliau."),
        arrayOf("SQlException", "Serverio duomenų bazės klaida.")
    )
    override fun getContents(): Array<Array<Any>> {
        return properties
    }
}