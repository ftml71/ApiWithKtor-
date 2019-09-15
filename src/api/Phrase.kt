package com.example.api

import com.example.API_VERSION
import com.example.model.Request
import com.example.repository.Repository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import model.EmojiPhrase

const val PHRASE_ENDPOINT_ADD = "$API_VERSION/phrase/add"
fun Route.phrase(db: Repository) {
    authenticate("auth") {
        post((PHRASE_ENDPOINT_ADD)) {
            val request = call.receive<Request>()
            val phrase = db.add(request.emoji, request.phrase)
            call.respond(phrase)
//        }
        }
    }


}