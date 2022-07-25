package com.example.tmdb.data.model.movie

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieModel(
    val id: Int,
    val title: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path")val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("vote_average") val voteAverage: Double
):Serializable{
    val baseUrlImage get() = "https://image.tmdb.org/t/p/w500"
}
