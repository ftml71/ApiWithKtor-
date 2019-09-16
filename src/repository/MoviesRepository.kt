package com.example.repository

import com.example.repository.DataBaseFactory.dbQuery
import model.Movie
import model.Movies
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class MoviesRepository : Repository {
    override suspend fun add(
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
    ) {
        transaction {
            Movies.insert {
                it[post_title] = titleValue
                it[post_description] = descriptionValue
                it[post_info] = infoValue
                it[post_summery] = summeryValue
                it[post_img] = imgValue
                it[post_date] = dateValue
                it[post_gitHub_url] = gitHubUrlValue
                it[post_slideShare_url] = slideShareUrlValue
                it[post_aparat_url] = aparatUrlValue
                it[post_logCat_url] = logCatUrlValue
                it[availability] = availabilityValue
                it[presenter_name] = presenterNameValue
                it[presenter_linkedin_url] = presenterLinkedinUrlValue
            }
        }
    }

    override suspend fun movie(id: Int): Movie? = dbQuery {
        Movies.select {
            (Movies.id eq id)
        }.mapNotNull { toMovie(it) }.singleOrNull()
    }

    override suspend fun movie(id: String): Movie? {
        return movie(id.toInt())
    }

    override suspend fun movies(): List<Movie> = dbQuery { Movies.selectAll().map { toMovie(it) } }


    override suspend fun remove(id: Int): Boolean {
        if (movie(id) == null) {
            throw IllegalArgumentException("No Movies found for id $id.")
        }
        return dbQuery { Movies.deleteWhere { Movies.id eq id } > 0 }
    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        Movies.deleteAll()
    }

    private fun toMovie(row: ResultRow): Movie =
        Movie(
            id = row[Movies.id].value,
            post_title = row[Movies.post_title],
            post_description = row[Movies.post_description],
            post_info = row[Movies.post_info],
            post_summery = row[Movies.post_summery],
            post_img = row[Movies.post_img],
            post_date = row[Movies.post_date],
            post_gitHub_url = row[Movies.post_gitHub_url],
            post_slideShare_url = row[Movies.post_slideShare_url],
            post_aparat_url = row[Movies.post_aparat_url],
            post_logCat_url = row[Movies.post_logCat_url],
            availability = row[Movies.availability],
            presenter_name = row[Movies.presenter_name],
            presenter_linkedin_url = row[Movies.presenter_linkedin_url]

        )
}