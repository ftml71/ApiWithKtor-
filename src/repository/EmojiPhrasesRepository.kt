package com.example.repository

import com.example.repository.DataBaseFactory.dbQuery
import model.EmojiPhrase
import model.EmojiPhrases
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class EmojiPhrasesRepository : Repository {
    override suspend fun add(emojiValue: String, phraseValue: String) {
        transaction {
            EmojiPhrases.insert {
                it[emoji] = emojiValue
                it[phrase] = phraseValue
            }
        }
    }

    override suspend fun phrase(id: Int): EmojiPhrase? = dbQuery {
        EmojiPhrases.select {
            (EmojiPhrases.id eq id)
        }.mapNotNull { toEmojiPhrase(it) }.singleOrNull()
    }

    override suspend fun phrase(id: String): EmojiPhrase? {
        return phrase(id.toInt())
    }

    override suspend fun phrases(): List<EmojiPhrase> = dbQuery { EmojiPhrases.selectAll().map { toEmojiPhrase(it) } }


    override suspend fun remove(id: Int): Boolean {
        if (phrase(id) == null) {
            throw IllegalArgumentException("No phrases found for id $id.")
        }
        return dbQuery { EmojiPhrases.deleteWhere { EmojiPhrases.id eq id } > 0 }
    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        EmojiPhrases.deleteAll()
    }

    private fun toEmojiPhrase(row: ResultRow): EmojiPhrase =
        EmojiPhrase(
            id = row[EmojiPhrases.id].value,
            emoji = row[EmojiPhrases.emoji],
            phrase = row[EmojiPhrases.phrase]

        )
}