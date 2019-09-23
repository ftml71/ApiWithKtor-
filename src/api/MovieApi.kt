package com.example.api

import com.example.API_VERSION
import com.example.api.requests.MoviesApiRequest
import com.example.apiUser
import com.example.repository.Repository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route

const val MOVIE_API_ENDPOINT = "$API_VERSION/movies"

@Location(MOVIE_API_ENDPOINT)
class MovieApi

fun Route.moviesApi(db: Repository) {
    authenticate("jwt") {
        get<MovieApi> {
            call.respond(db.movies())
        }

        post<MovieApi> {
            val user = call.apiUser!!
            try {
                val request = call.receive<MoviesApiRequest>()
                val movie = db.add(
                    user.userId,
                    request.post_title,
                    request.post_description,
                    request.post_info,
                    request.post_summery,
                    request.post_img,
                    request.post_date,
                    request.post_gitHub_url,
                    request.post_slideShare_url,
                    request.post_aparat_url,
                    request.post_logCat_url,
                    request.availability,
                    request.presenter_name,
                    request.presenter_linkedin_url
                )
                if (movie != null) {
                    call.respond(movie)
                } else {
                    call.respondText("Invalid data received", status = HttpStatusCode.InternalServerError)

                }
            } catch (e: Throwable) {
                call.respondText("Invalid data received", status = HttpStatusCode.BadRequest)
            }
        }
    }
}
//import com.example.API_VERSION
//import com.example.model.Request
//import com.example.repository.Repository
//import io.ktor.application.call
//import io.ktor.request.receive
//import io.ktor.response.respond
//import io.ktor.routing.Route
//import io.ktor.routing.get
//import io.ktor.routing.post
//
//const val PHRASE_ENDPOINT_ADD = "$API_VERSION/movie/add"
//const val PHRASE_ENDPOINT_GET = "$API_VERSION/movie/get"
//fun Route.movie(db: Repository) {
////    authenticate("auth") {
//    post((PHRASE_ENDPOINT_ADD)) {
//        val request = call.receive<Request>()
//        val movie = db.add(
//            "",
//            request.post_title,
//            request.post_description,
//            request.post_info,
//            request.post_summery,
//            request.post_img,
//            request.post_date,
//            request.post_gitHub_url,
//            request.post_slideShare_url,
//            request.post_aparat_url,
//            request.post_logCat_url,
//            request.availability,
//            request.presenter_name,
//            request.presenter_linkedin_url
//        )
//        call.respond(movie)
////        }
//    }
//
//    get((PHRASE_ENDPOINT_GET)) {
//        val movies = db.movies()
//        call.respond(movies)
////        }
//    }
////}
//
//
//}