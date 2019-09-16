package com.example.repository

import model.Movie

interface Repository {
    suspend fun add(
        titleValue: String,
        descriptionValue: String,
        infoValue: String,
        summeryValue: String,
        imgValue: String,
        dateValue: String,
        gitHubUrlValue: String,
        slideShareUrlValue: String,
        aparatUrlValue: String,
        logCatUrlValue: String,
        availabilityValue: String,
        presenterNameValue: String,
        presenterLinkedinUrlValue: String
    )

    suspend fun movie(id: Int): Movie?
    suspend fun movie(id: String): Movie?
    suspend fun movies(): List<Movie>
    //    suspend fun remove(post_description: Movie)
    suspend fun remove(id: Int): Boolean

    suspend fun remove(id: String): Boolean
    suspend fun clear()
}