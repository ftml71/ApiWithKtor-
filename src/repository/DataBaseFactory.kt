package com.example.repository

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.Movies
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction


object DataBaseFactory {
    fun init() {
        Database.connect(hikari())
        transaction {
            SchemaUtils.create(Movies)

//            Movies.insert {
//                it[post_title] = "titleValue"
//                it[post_description] = "descriptionValue"
//                it[post_info] = "infoValue"
//                it[post_summery] = "summeryValue"
//                it[post_img] = "imgValue"
//                it[post_date] = "dateValue"
//                it[post_gitHub_url] = "gitHubUrlValue"
//                it[post_slideShare_url] = "slideShareUrlValue"
//                it[post_aparat_url] = "aparatUrlValue"
//                it[post_logCat_url] = "logCatUrlValue"
//                it[availability] = "availabilityValue"
//                it[presenter_name] = "presenterNameValue"
//                it[presenter_linkedin_url] = "presenterLinkedinUrlValue"
//            }
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()


        config.driverClassName = "com.mysql.cj.jdbc.Driver"
        config.jdbcUrl = "jdbc:mysql://ftml71:15101371@localhost:3306/ftml?useUnicode=true&serverTimezone=UTC"
        config.maximumPoolSize = 3
        config.isAutoCommit = false

        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.addDataSourceProperty("characterEncoding","utf8")
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(
        block: () -> T
    ): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}