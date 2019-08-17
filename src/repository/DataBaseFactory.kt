package com.example.repository

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
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

         config.driverClassName = "org.h2.Driver"
        config.jdbcUrl = "jdbc:h2:mem:test"
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.setTransactionIsolation("TRANSACTION_REPEATABLE_READ")
        return HikariDataSource(config)
    }
}