package common.entities

import java.util.Scanner
import java.io.Serializable

class User: Serializable {
    private var login = "user"
    private var password = "1234"
    private var token: String= "null"

    fun setToken(t: String) {token = t}
    fun getToken() = token

    fun setVars() {
        val scanner = Scanner(System.`in`)
        print("Login:")
        login = scanner.nextLine()
        print("Password:")
        password = scanner.nextLine()
    }
}
