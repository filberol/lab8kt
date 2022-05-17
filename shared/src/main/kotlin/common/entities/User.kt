package common.entities

import java.io.Serializable
import java.security.MessageDigest
import java.util.Scanner

class User: Serializable {
    private var login = "user"
    private var password = "1234"
    private var token: String= "null"
    private val salt = "nk_07%^_VV"

    fun setToken(t: String) {token = t}
    fun getToken() = token
    fun getLogin() = login
    fun getPass() = password

    fun readVars() {
        val scanner = Scanner(System.`in`)
        print("Login: ")
        login = scanner.nextLine()
        print("Password: ")
        password = encodePass(scanner.nextLine())
    }

    fun readVars(log: String, pass: String) {
        login = log
        password = encodePass(pass)
    }

    private fun encodePass(pass: String): String {
        val encoder = MessageDigest.getInstance("SHA-256")
        return salt + encoder.digest(pass.toByteArray()).toString()
    }

    override fun toString(): String {
        return "User(name=$login, password=$password, token=$token)"
    }
}
