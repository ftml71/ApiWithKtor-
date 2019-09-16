package com.example.api

import com.example.API_VERSION
import com.example.model.Request
import com.example.repository.Repository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post

const val PHRASE_ENDPOINT_ADD = "$API_VERSION/movie/add"
const val PHRASE_ENDPOINT_GET = "$API_VERSION/movie/get"
fun Route.movie(db: Repository) {
    authenticate("auth") {
        post((PHRASE_ENDPOINT_ADD)) {
            val request = call.receive<Request>()
            val movie = db.add(request. post_title,
                request. post_description,
                request. post_info,
                request. post_summery,
                request. post_img,
                request. post_date,
                request. post_gitHub_url,
                request. post_slideShare_url,
                request. post_aparat_url,
                request. post_logCat_url,
                request. availability,
                request. presenter_name,
                request. presenter_linkedin_url)
            call.respond(movie)
//        }
        }

        get((PHRASE_ENDPOINT_GET)) {
            val movies = db.movies()
            call.respond(movies)
//        }
        }
    }


}