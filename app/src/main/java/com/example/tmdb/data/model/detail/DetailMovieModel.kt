package com.example.tmdb.data.model.detail

import com.example.tmdb.data.model.genre.GenreModel
import java.io.Serializable

data class DetailMovieModel(
    val backdrop_path: String,
    val budget: Int,
    val genres: List<GenreModel>,
    val id: Int,
    val original_title: String,
    val original_language: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val tagline: String,
    val status: String,
    val title: String,
) : Serializable {
    val baseUrlImage get() = "https://image.tmdb.org/t/p/w500"
}