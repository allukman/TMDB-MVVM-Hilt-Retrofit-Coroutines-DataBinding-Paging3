package com.example.tmdb.data.model.genre

import java.io.Serializable

data class GenreModel(
    val id: Int,
    val name: String,
    var isSelected: Boolean = false
) : Serializable
