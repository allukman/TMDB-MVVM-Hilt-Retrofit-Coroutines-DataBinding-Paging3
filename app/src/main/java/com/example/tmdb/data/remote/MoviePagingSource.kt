package com.example.tmdb.data.remote

import androidx.paging.PagingSource
import com.example.tmdb.data.model.movie.MovieModel
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class MoviePagingSource(
    private val movieApi: MovieApi,
    private val type: String,
    private val genres: String?,
) : PagingSource<Int, MovieModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response = if (genres != null) {
                movieApi.getMovieByGenre(genres)
            } else {
                if (type == "popular") {
                    movieApi.getPopularMovies(position)
                } else {
                    movieApi.getTopRatedMovies(position)
                }
            }
            val movies = response.results
            LoadResult.Page(
                data = movies,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

}