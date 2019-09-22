package com.example

import io.ktor.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
const val MIN_USER_ID_LENGTH=4
const val MIN_PASSWORD_LENGTH =6
val hashKey = hex(System.getenv("SECRET_KEY"))
val hmacKey= SecretKeySpec(hashKey,"HmacSHA1")
fun hash(password : String):String{
    val hmac = Mac.getInstance("HmacSHA1")
    hmac.init(hmacKey)
    return hex(hmac.doFinal(password.toByteArray(Charsets.UTF_8)))

}
private val userIDPattern = "[a-aA-Z0-9_\\.]+".toRegex()
internal fun userNameValid(userId :String)=userId.matches(userIDPattern)