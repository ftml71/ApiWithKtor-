package model

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Column
import java.io.Serializable

data class Movie(
    val id: Int,
    val userId: String,
    val post_title: String?,
    val post_description: String?,
    val post_info: String?,
    val post_summery: String?,
    val post_img: String?,
    val post_date: String?,
    val post_gitHub_url: String?,
    val post_slideShare_url: String?,
    val post_aparat_url: String?,
    val post_logCat_url: String?,
    val availability: String?,
    val presenter_name: String?,
    val presenter_linkedin_url: String?

) : Serializable

object Movies : IntIdTable() {
    val user: Column<String> = varchar("user_id", 20). index()
    val post_title: Column<String?> = text("post_title").nullable()
    val post_description: Column<String?> = text("post_description").nullable()
    val post_info: Column<String?> = text("post_info").nullable()
    val post_summery: Column<String?> = text("post_summery").nullable()
    val post_img: Column<String?> = text("post_img").nullable()
    val post_date: Column<String?> = text("post_date").nullable()
    val post_gitHub_url: Column<String?> = text("post_gitHub_url").nullable()
    val post_slideShare_url: Column<String?> = text("post_slideShare_url").nullable()
    val post_aparat_url: Column<String?> = text("post_aparat_url").nullable()
    val post_logCat_url: Column<String?> = text("post_logCat_url").nullable()
    val availability: Column<String?> = text("availability").nullable()
    val presenter_name: Column<String?> = text("presenter_name").nullable()
    val presenter_linkedin_url: Column<String?> = text("presenter_linkedin_url").nullable()
}