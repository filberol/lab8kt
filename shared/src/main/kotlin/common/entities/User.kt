package common.entities

import java.util.Scanner
import java.io.Serializable
import java.security.MessageDigest

class User: Serializable {
    private var login = "user"
    private var password = "1234"
    private var token: String= "null"

    fun setToken(t: String) {token = t}
    fun getToken() = token
    fun getLogin() = login
    fun getPass() = password

    fun setVars() {
        val scanner = Scanner(System.`in`)
        val encoder = MessageDigest.getInstance("SHA-256")
        print("Login:")
        login = scanner.nextLine()
        print("Password:")
        password = encoder.digest(scanner.nextLine().toByteArray()).toString()
    }

    override fun toString(): String {
        return "User(name=$login, password=$password, token=$token)"
    }
}
