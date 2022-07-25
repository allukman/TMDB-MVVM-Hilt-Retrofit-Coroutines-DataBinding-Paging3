package com.example.tmdb.data.model.reviews

import java.io.Serializable

data class AuthorDetails(
    val avatar_path: String,
    val name: String,
    val rating: Double,
    val username: String
) : Serializable