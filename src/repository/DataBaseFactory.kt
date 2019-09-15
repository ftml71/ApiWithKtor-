package com.example.repository

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.EmojiPhrases
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction


object DataBaseFactory {
    fun init() {
        Database.connect(hikari())
        transaction {
            SchemaUtils.create(EmojiPhrases)
            EmojiPhrases.insert {
                it[emoji] = "e1"
                it[phrase] = "p1"
            }
            EmojiPhrases.insert {
                it[emoji] = "e2"
                it[phrase] = "p2"
            }
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()


        config.driverClassName = "com.mysql.cj.jdbc.Driver"
        config.jdbcUrl = "jdbc:mysql://ftml71:15101371@localhost:3306/ftml?useUnicode=true&serverTimezone=UTC"
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(
        block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}