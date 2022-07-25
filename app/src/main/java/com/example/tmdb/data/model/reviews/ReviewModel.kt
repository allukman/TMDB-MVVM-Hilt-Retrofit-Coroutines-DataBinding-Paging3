package com.example.tmdb.data.model.reviews

import java.io.Serializable

data class ReviewModel(
    val author: String,
    val author_details: AuthorDetails,
    val content: String,
    val created_at: String,
    val id: String,
    val updated_at: String,
    val url: String
) : Serializable {
    val baseUrlImage get() = "https://image.tmdb.org/t/p/w500"
}