package com.example

import com.example.api.phrase
import com.example.model.User
import com.example.repository.InMemoryRepository
import com.example.webapp.home
import com.example.webapp.phrases
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.basic
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.freemarker.FreeMarker
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.locations.Locations
import io.ktor.locations.locations
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.routing


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
        install(Authentication) {
            basic(name = "auth") {
                realm = "Ktor Server"
                validate { credentials ->
                    if (credentials.password == "${credentials.name}123") User(credentials.name) else null

                }
            }
        }
        install(Locations)
        install(ContentNegotiation) {
            gson()

        }
        val db = InMemoryRepository()
        routing {
            static("/static") {
                resources("images")
            }
            home()
            phrase(db)
            phrases(db)
        }

    }
}

suspend fun ApplicationCall.redirect(location: Any) {
    respondRedirect(application.locations.href(location))
}

