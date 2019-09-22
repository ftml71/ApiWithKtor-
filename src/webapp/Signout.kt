package com.example.webapp

import com.example.model.EPSession
import com.example.redirect
import io.ktor.application.call
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.routing.Route
import io.ktor.sessions.clear
import io.ktor.sessions.sessions


const val SIGNOUT = "/signout"

@Location(SIGNOUT)
class Signout

fun Route.signout() {
    get<Signout> {
        call.sessions.clear<EPSession>()
        call.redirect(SignIn())
    }
}