package com.example.tmdb.data.remote

import com.example.tmdb.BuildConfig
import com.example.tmdb.data.model.detail.DetailMovieModel
import com.example.tmdb.data.model.detail.VideoResponse
import com.example.tmdb.data.model.genre.GenreResponse
import com.example.tmdb.data.model.movie.MovieResponse
import com.example.tmdb.data.model.reviews.ReviewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = BuildConfig.API_KEY
    }

    @GET("movie/popular?api_key=$API_KEY")
    suspend fun getPopularMovies(
        @Query("page") position: Int
    ) : MovieResponse

    @GET("movie/top_rated?api_key=$API_KEY")
    suspend fun getTopRatedMovies(
        @Query("page") position: Int
    ) : MovieResponse

    @GET("movie/upcoming?api_key=$API_KEY")
    suspend fun getUpcomingMovie(): Response<MovieResponse>

    @GET("discover/movie?api_key=$API_KEY")
    suspend fun getMovieByGenre(
        @Query("with_genres") genres: String
    ) : MovieResponse

    @GET("genre/movie/list?api_key=$API_KEY")
    suspend fun getListGenre(): Response<GenreResponse>

    @GET("movie/{movieId}?api_key=$API_KEY")
    suspend fun getDetailMovie(@Path("movieId")movieId: Int): Response<DetailMovieModel>

    @GET("movie/{movieId}/videos?api_key=$API_KEY")
    suspend fun getMovieTrailer(@Path("movieId")movieId: Int): Response<VideoResponse>

    @GET("movie/{movieId}/reviews?api_key=$API_KEY")
    suspend fun getMovieReviews(@Path("movieId")movieId: Int): Response<ReviewsResponse>

}