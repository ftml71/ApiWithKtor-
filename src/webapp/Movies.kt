package com.example.webapp

import com.example.model.User
import com.example.redirect
import com.example.repository.Repository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.routing.Route

const val PHRASES = "/movies"

@Location(PHRASES)
class Movies

fun Route.Movies(db: Repository) {
    authenticate("auth") {

        get<Movies> {
            val user = call.authentication.principal as User
            val movies = db.movies()
            call.respond(
                FreeMarkerContent(
                    "movies.ftl", mapOf(
                        "movies" to movies,
                        "displayName" to user.displayName
                    )
                )
            )
        }
        post<Movies> {
            val params = call.receiveParameters()
            val action = params["action"] ?: throw IllegalArgumentException("Missing parameter: action")
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
    }
}