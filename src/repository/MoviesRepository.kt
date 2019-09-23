package com.example.repository

import com.example.model.User
import com.example.model.Users
import com.example.repository.DataBaseFactory.dbQuery
import model.Movie
import model.Movies
import org.jetbrains.exposed.sql.*

class MoviesRepository : Repository {


    override suspend fun add(
        userId: String,
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
    ) = dbQuery {
        val insertStatement = Movies.insert {
            it[user] = userId
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
        val result = insertStatement.resultedValues?.get(0)
        if (result != null) {
            toMovie(result)
        } else {
            null
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

    override suspend fun user(userId: String, hash: String?): User? {
        val user = dbQuery {
            Users.select {
                (Users.id eq userId)
            }.mapNotNull { toUser(it) }.singleOrNull()
        }
        return when {
            user == null -> null
            hash == null -> user
            user.passwordHash == hash -> user
            else -> null
        }
    }

    override suspend fun userByEmail(email: String) = dbQuery {
        Users.select { Users.email.eq(email) }
            .map {
                User(
                    it[Users.id],
                    email,
                    it[Users.displayName],
                    it[Users.passwordHash]
                )
            }.singleOrNull()
    }

    override suspend fun userById(userId: String) = dbQuery {
        Users.select { Users.id.eq(userId) }
            .map { User(userId, it[Users.email], it[Users.displayName], it[Users.passwordHash]) }.singleOrNull()
    }

    override suspend fun createUser(user: User) {
        Users.insert {
            it[id] = user.userId
            it[displayName] = user.displayName
            it[email] = user.email
            it[passwordHash] = user.passwordHash
        }
        Unit

    }

    private fun toMovie(row: ResultRow): Movie =
        Movie(
            id = row[Movies.id].value,
            userId = row[Movies.user],
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

    private fun toUser(row: ResultRow): User =
        User(
            userId = row[Users.id],
            email = row[Users.email],
            displayName = row[Users.displayName],
            passwordHash = row[Users.passwordHash]
        )
}