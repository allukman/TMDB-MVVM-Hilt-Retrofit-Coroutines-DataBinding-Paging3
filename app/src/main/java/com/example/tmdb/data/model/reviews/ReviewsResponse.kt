package com.example.tmdb.data.model.reviews

import java.io.Serializable

data class ReviewsResponse(
    val id: Int,
    val page: Int,
    val results: List<ReviewModel>,
    val total_pages: Int,
    val total_results: Int
) : Serializable