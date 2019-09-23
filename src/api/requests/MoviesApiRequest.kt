package com.example.api.requests

data class MoviesApiRequest(
    val post_title: String,
    val post_description: String,
    val post_info: String,
    val post_summery: String,
    val post_img: String,
    val post_date: String,
    val post_gitHub_url: String,
    val post_slideShare_url: String,
    val post_aparat_url: String,
    val post_logCat_url: String,
    val availability: String,
    val presenter_name: String,
    val presenter_linkedin_url: String
)