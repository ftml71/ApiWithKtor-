package com.example

import com.example.api.movie
import com.example.model.EPSession
import com.example.model.User
import com.example.repository.DataBaseFactory
import com.example.repository.MoviesRepository
import com.example.webapp.*
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.freemarker.FreeMarker
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.locations.Locations
import io.ktor.locations.locations
import io.ktor.request.header
import io.ktor.request.host
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.routing
import io.ktor.sessions.SessionTransportTransformer
import io.ktor.sessions.SessionTransportTransformerMessageAuthentication
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import java.net.URI
import java.util.concurrent.TimeUnit


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)
const val API_VERSION = "/api/v1"
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(DefaultHeaders)
    install(StatusPages) {
        exception<Throwable> { e ->
            call.respondText(
                e.localizedMessage,
                ContentType.Text.Plain, HttpStatusCode.InternalServerError
            )
        }
        install(FreeMarker)
        {
            templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        }
//        install(Authentication) {
//            basic(name = "auth") {
//                realm = "Ktor Server"
//                validate { credentials ->
//                    if (credentials.password == "${credentials.name}123") User(credentials.name) else null
//
//                }
//            }
//        }
        install(ContentNegotiation) {
            gson()

        }
        install(Locations)
        install(Sessions) {
            cookie<EPSession>("SESSION"){
                transform(SessionTransportTransformerMessageAuthentication(hashKey))
            }
        }


        val hashFunction = { s: String -> hash(s) }

        DataBaseFactory.init()

        val db = MoviesRepository()
        routing {
            static("/static") {
                resources("images")
            }
            home(db)
            aboute(db)
            movie(db)
            Movies(db,hashFunction)
            signin(db, hashFunction)
            signout()
            signup(db, hashFunction)
        }

    }
}

suspend fun ApplicationCall.redirect(location: Any) {
    respondRedirect(application.locations.href(location))
}


fun ApplicationCall.refererHost() = request.header(HttpHeaders.Referrer)?.let { URI.create(it).host }
fun ApplicationCall.securityCode(date: Long, user: User, hashFunction: (String) -> String) =
    hashFunction("$date:${user.userId}:${request.host()}:${refererHost()}")


fun ApplicationCall.verifyCode(date: Long, user: User, code: String, hashFunction: (String) -> String) =
    securityCode(date, user, hashFunction) == code &&
            (System.currentTimeMillis() - date).let { it > 0 && it < TimeUnit.MILLISECONDS.convert(2, TimeUnit.HOURS) }

