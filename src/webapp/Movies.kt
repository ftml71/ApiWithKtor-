package com.example.webapp

import com.example.model.EPSession
import com.example.redirect
import com.example.repository.Repository
import com.example.securityCode
import com.example.verifyCode
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions

const val PHRASES = "/movies"

@Location(PHRASES)
class Movies

fun Route.Movies(db: Repository, hashFunction: (String) -> String) {
//    authenticate("auth") {

    get<Movies> {
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }

//        val user = call.authentication.principal as User
        if (user == null) {
            call.redirect(SignIn())
        } else {
            val movies = db.movies()
            val date = System.currentTimeMillis()
            val code = call.securityCode(date, user, hashFunction)
            call.respond(
                FreeMarkerContent(
                    "movies.ftl", mapOf(
                        "movies" to movies,
                        "user" to user,
                        "date" to date,
                        "code" to code
                    ), user.userId
                )
            )
        }
    }
    post<Movies> {
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }

        val params = call.receiveParameters()
        val date = params["date"]?.toLongOrNull() ?: return@post call.redirect(it)
        val code = params["code"] ?: return@post call.redirect(it)
        val action = params["action"] ?: throw IllegalArgumentException("Missing parameter: action")






        if (user == null || !call.verifyCode(date, user, code, hashFunction)) {
            call.redirect(SignIn())
        }

        when (action) {
            "delete" -> {
                val id = params["id"] ?: throw IllegalArgumentException("Missing parameter: id")
                db.remove(id)
            }
            "add" -> {
                val title =
                    params["post_title"] ?: throw  IllegalArgumentException("Missing parammeter: post_title")
                val description = params["post_description"]
                    ?: throw  IllegalArgumentException("Missing parammeter: post_description")
                val info = params["post_info"] ?: throw  IllegalArgumentException("Missing parammeter: post_info")
                val summery =
                    params["post_summery"] ?: throw  IllegalArgumentException("Missing parammeter: post_summery")
                val img = params["post_img"] ?: throw  IllegalArgumentException("Missing parammeter: post_img")
                val date = params["post_date"] ?: throw  IllegalArgumentException("Missing parammeter: post_date")
                val gitHub_url = params["post_gitHub_url"]
                    ?: throw  IllegalArgumentException("Missing parammeter: post_gitHub_url")
                val slideShare_url = params["post_slideShare_url"]
                    ?: throw  IllegalArgumentException("Missing parammeter: post_slideShare_url")
                val aparat_url = params["post_aparat_url"]
                    ?: throw  IllegalArgumentException("Missing parammeter: post_aparat_url")
                val logCat_url = params["post_logCat_url"]
                    ?: throw  IllegalArgumentException("Missing parammeter: post_logCat_url")
                val availability =
                    params["availability"] ?: throw  IllegalArgumentException("Missing parammeter: availability")
                val presenter_name = params["presenter_name"]
                    ?: throw  IllegalArgumentException("Missing parammeter: presenter_name")
                val presenter_linkedin_url = params["presenter_linkedin_url"]
                    ?: throw  IllegalArgumentException("Missing parammeter: presenter_linkedin_url")
                db.add(
                    user!!.userId,
                    title,
                    description,
                    info,
                    summery,
                    img,
                    date,
                    gitHub_url,
                    slideShare_url,
                    aparat_url,
                    logCat_url,
                    availability,
                    presenter_name,
                    presenter_linkedin_url


                )
            }
        }
        call.redirect(Movies())
    }
//    }
}