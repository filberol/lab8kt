package lab6server.server

import kotlin.random.Random

/**
 * Class tokens-storage for user token pool.
 * Tokens stored locally.
 */
class TokenManager {
    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    private val stringLength = 30
    private val tokenPool = ArrayList<String>()

    /**
     * Generates token and adds to pool.
     */
    fun generateAddToken(): String {
        val randomString = (1..stringLength)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
        tokenPool.add(randomString)
        return randomString
    }

    /**
     * Checks token and expires(removes) it.
     */
    fun checkPopToken(token: String): Boolean {
        return tokenPool.contains(token).also { tokenPool.remove(token) }
    }
}