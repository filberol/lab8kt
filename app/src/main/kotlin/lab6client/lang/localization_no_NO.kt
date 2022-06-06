package lab6client.lang

import java.util.*

class localization_no_NO: ListResourceBundle() {
    private val properties: Array<Array<Any>> = arrayOf(
        arrayOf("Welcome", " Menneskelig samling ver."),
        arrayOf("TabHeading", " _____________________________________________________________________________________________________________________________________________________________________________________________________"),
        arrayOf("TabHead", "    |  ID  | Navn                         | Koordinater  | Opprettelsesdato                                   | Høyde  | Fødselsdag | Øyenfarge  | Hair color | PlaCords    | Stedsnavn                 |"),
        arrayOf("MidLine", "    |------|------------------------------|--------------|----------------------------------------------------|--------|------------|------------|------------|-------------|---------------------------|"),
        arrayOf("EndLine", "    |______|______________________________|______________|____________________________________________________|________|____________|____________|____________|_____________|___________________________|"),
        arrayOf("ConfigError", " Feil under lesing av config, avslutter..."),
        arrayOf("Commitment", " Vil du fortsette? (J/N)"),
        arrayOf("RedundantArgs", " Kommandoen krever ingen argumenter"),
        arrayOf("NotEnoughArgs", " Kommando krever gyldige argumenter!"),
        arrayOf("SyntaxException", " Ugyldig syntaks!"),
        arrayOf("LoopExecute", " Filen er skriptet blir allerede utført! Hoppet over..."),
        arrayOf("NoHistory", " -Ingen tidligere historie-"),
        arrayOf("HistHeading", " --Siste %3d kommandoer--"),
        arrayOf("NoSuchCommand", " er ikke en indre eller ytre kommando, kjørbart skript eller pakkefil"),
        arrayOf("CollectionLoad", " Laster inn samling fra fil..."),
        arrayOf("DataFileNotFound", " - Datafilen ble ikke funnet. Ingen data vil bli lastet inn"),
        arrayOf("DataNoAccess", " - Ingen tilgang til angitt datafil. Data vil ikke bli lastet inn"),
        arrayOf("DataIOError", " - Ubehandlet IOException under lesing av datafilen. Ingen data vil bli lastet inn"),
        arrayOf("EmptyCollection", " Samlingen er tom!"),
        arrayOf("Done", " Ferdig."),
        arrayOf("Total", " Totalt - %d elementer"),
        arrayOf("FieldList", "Navn,Koordinat X - Dobbel,Koordinat Y - Heltall ,Høyde - >0,Fødselsdag/Eksempel>2020-06-22,Øyefarge/BlÅ ORANSJE BRUNN,Hårfarge/RØD BLÅ GUL ORANSJE,Plasseringskoordinat X - Float, Plasseringskoordinat Y - Float, Plasseringsnavn"),
        arrayOf("TypeField", " Vennligst skriv inn felt>"),
        arrayOf("EmptySearch", " Tomt søk!"),
        arrayOf("CommandList", "help/info/show/add/update/remove_by_id/clear/save/execute_script/exit/remove_first/remove_lower/history/filter_greater_than_birthday/print_ascending/print_field_descending_location"),
        arrayOf("Recon/necting", " Prøver å koble til serveren..."),
        arrayOf("ConnectionAccept", " Koblet til serveren."),
        arrayOf("ConnectionFalse", " Kan ikke koble til adressen... Kontroller egnethet."),
        arrayOf("ConnectionFail", " Samlingsserver er ikke tilgjengelig..."),
        arrayOf("CannotConnect", " Kan ikke koble til serveren. Prøv senere."),
        arrayOf("ReconnectIn", " Kobler til på nytt om 10 sekunder..."),
        arrayOf("NotConnected", " Ikke koblet til server!"),
        arrayOf("DataLoss", " Forbindelse mistet! Prøver å koble til på nytt..."),
        arrayOf("NoResponse", " Serveren svarte ikke riktig..."),
        arrayOf("Offline", " Fortsett om du vil være frakoblet."),
        arrayOf("Closing", "Avslutter forbindelse til serveren..."),
        arrayOf("RequestError", "Mistet forbindelsen til serveren. Koble til på nytt"),
        arrayOf("NewAcc", "Registrert ny konto>>>"),
        arrayOf("SucAcc", "Vellykket pålogging>>>"),
        arrayOf("Queue", "Server under høy belastning. Venter på data..."),
        arrayOf("CliMode", "Konsollmodus!"),
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