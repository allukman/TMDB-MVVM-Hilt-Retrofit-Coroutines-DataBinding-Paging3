package com.example.tmdb.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.tmdb.data.model.detail.DetailMovieModel
import com.example.tmdb.data.model.detail.VideoResponse
import com.example.tmdb.data.model.genre.GenreResponse
import com.example.tmdb.data.model.movie.MovieResponse
import com.example.tmdb.data.model.reviews.ReviewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val movieApi: MovieApi) {
    fun getPopularMovies() =
        Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(movieApi, "popular", null) }
        ).liveData

    fun getTopRatedMovies() =
        Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(movieApi, "toprated", null) }
        ).liveData

    fun getMoviesByGenreMovies(genres: String) =
        Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(movieApi, "filter", genres) }
        ).liveData

    suspend fun getUpcomingMovie(): Response<MovieResponse> = withContext(
        Dispatchers.IO
    ) {
        val upcoming = movieApi.getUpcomingMovie()
        upcoming
    }

    suspend fun getListGenre(): Response<GenreResponse> = withContext(
        Dispatchers.IO
    ) {
        val genre = movieApi.getListGenre()
        genre
    }

    suspend fun getDetailMovie(id: Int): Response<DetailMovieModel> = withContext(
        Dispatchers.IO
    ) {
        val movie = movieApi.getDetailMovie(id)
        movie
    }

    suspend fun getTrailerMovie(id: Int): Response<VideoResponse> = withContext(
        Dispatchers.IO
    ) {
        val movie = movieApi.getMovieTrailer(id)
        movie
    }

    suspend fun getReviewsMovie(id: Int): Response<ReviewsResponse> = withContext(
        Dispatchers.IO
    ) {
        val movie = movieApi.getMovieReviews(id)
        movie
    }
}