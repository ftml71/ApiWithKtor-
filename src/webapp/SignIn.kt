package com.example.webapp

import com.example.MIN_PASSWORD_LENGTH
import com.example.MIN_USER_ID_LENGTH
import com.example.model.EPSession
import com.example.redirect
import com.example.repository.Repository
import com.example.userNameValid
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.Parameters
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set

const val SIGNIN = "/signin"

@Location(SIGNIN)
data class SignIn(val userId: String = "", val error: String = "")

fun Route.signin(db: Repository, hasFunction: (String) -> String) {
    post<SignIn> {
        val params = call.receive<Parameters>()
        val userId = params["userId"] ?: return@post call.redirect(it)
        val password = params["password"] ?: return@post call.redirect(it)

        val signInError = SignIn(userId)
        val signin = when {
            userId.length < MIN_USER_ID_LENGTH -> null
            password.length < MIN_PASSWORD_LENGTH -> null
            !userNameValid(userId) -> null
            else -> db.user(userId, hasFunction(password))
        }

        if (signin == null) {
            call.redirect(signInError.copy(error = "Invalid username or password"))
        } else {
            call.sessions.set(EPSession(signin.userId))
            call.redirect(Movies())
        }
    }

    get<SignIn> {
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }
        if (user != null) {
            call.redirect(Home())
        } else {
            call.respond(FreeMarkerContent("signin.ftl", mapOf("userId" to it.userId, "error" to it.error)))
        }
    }

}