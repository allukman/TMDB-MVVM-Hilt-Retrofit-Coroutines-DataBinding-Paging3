package com.example.tmdb.data.model.genre

import com.example.tmdb.data.model.genre.GenreModel
import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @field:SerializedName("genres") val genres : List<GenreModel>,
)