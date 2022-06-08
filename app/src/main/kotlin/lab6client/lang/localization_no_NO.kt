package lab6client.lang

import java.util.*

class localization_no_NO: ListResourceBundle() {
    private val properties: Array<Array<Any>> = arrayOf(
        arrayOf("Welcome", " Menneskelig samling ver."),

        arrayOf("EmptyCollection", " Samlingen er tom!"),

        arrayOf("FieldList", "Navn,Koordinat X - Dobbel,Koordinat Y - Heltall ,Høyde - >0,Fødselsdag/Eksempel>2020-06-22,Øyefarge/BlÅ ORANSJE BRUNN,Hårfarge/RØD BLÅ GUL ORANSJE,Plasseringskoordinat X - Float, Plasseringskoordinat Y - Float, Plasseringsnavn"),

        arrayOf("RegTitle", "Registrering/autorisasjon"),
        arrayOf("Console", "Konsoll"),
        arrayOf("Table", "Tabell"),
        arrayOf("Title", "Menneskelig samling"),
        arrayOf("LoginLabel", "|----<Pålogging>----|"),
        arrayOf("PassLabel", "|--<Passord>--|"),
        arrayOf("Re-login", "Logg inn på nytt"),
        arrayOf("Exit", "Avslutt"),
        arrayOf("Refresh", "Oppdater"),
        arrayOf("Graphic", "Grafisk info"),
        arrayOf("LoginRule", "Pålogging bør ha minst 4 symboler"),
        arrayOf("PassRule", "Passord skal være minst 4 symboler"),
        arrayOf("Add", "Legg til"),
        arrayOf("Cancel", "Avbryt"),
        arrayOf("OK", "OK"),
        arrayOf("AskId", "|-<Skriv inn objekt-ID>-|"),
        arrayOf("Remove", "Slett"),
        arrayOf("Wait", "Vente..."),
        arrayOf("Execute", "Skriv inn hele banen til filen"),
        arrayOf("ButtEx", "Henrette"),
        arrayOf("Update", "Oppdater ID"),
        arrayOf("Birthday", "Fødselsdag"),
        arrayOf("Coordinates", "Koordinater"),
        arrayOf("CreationDate", "Opprettelsesdato"),
        arrayOf("EyeColor", "Øyenfarge"),
        arrayOf("HairColor", "Hårfarge"),
        arrayOf("Height", "Høyde"),
        arrayOf("Id", "Id"),
        arrayOf("Location", "Plassering"),
        arrayOf("Name", "Navn"),
        arrayOf("Owner", "Eieren"),
        arrayOf("CoordinateInf", "Koordinat info"),
        arrayOf("CFile", "Finne..."),
        arrayOf("Blue", "Blå"),
        arrayOf("Orange", "Oransje"),
        arrayOf("Brown", "Brun"),
        arrayOf("Red", "Rød"),
        arrayOf("Yellow", "Gul"),
        arrayOf("ChoTitle", "Filskript"),
        arrayOf("ChoFilt", "Tekstfiler (*.txt)"),
        arrayOf("LoginPassRule", "Innlogging og passord skal bestå av minst 4 symboler"),
        arrayOf("Login", "Logg Inn"),
        arrayOf("Register", "Registrere"),
        arrayOf("UseExi", "Denne påloggingen er allerede tatt."),
        arrayOf("WrLogin", "Innlogging eller passord er feil."),
        arrayOf("Fail", "Tilkoblingen mislyktes. Prøv igjen senere."),
        arrayOf("SQlException", "Serverdatabasefeil.")
    )
    override fun getContents(): Array<Array<Any>> {
        return properties
    }
}