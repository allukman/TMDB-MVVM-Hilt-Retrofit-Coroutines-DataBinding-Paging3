package com.example.tmdb.ui.genre

import androidx.hilt.Assisted
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.tmdb.data.remote.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val GENRE = "GENRE"
        private const val EMPTY = ""
    }

    private val currentGenre = state.getLiveData(GENRE, EMPTY)
    val getMovieByGenre = currentGenre.switchMap { query ->
        if(!query.isEmpty()) {
            repository.getMoviesByGenreMovies(query).cachedIn(viewModelScope)
        } else {
            repository.getPopularMovies().cachedIn(viewModelScope)
        }
    }

    fun filterByGenre(genre: String) {
        currentGenre.value = genre
    }

}