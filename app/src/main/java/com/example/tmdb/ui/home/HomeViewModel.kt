package com.example.tmdb.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.tmdb.data.model.movie.MovieResponse
import com.example.tmdb.data.remote.MovieRepository
import com.example.tmdb.util.Helper.hasInternetConnection
import com.example.tmdb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    val movieUpComing: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()
    val popularMovies = repository.getPopularMovies().cachedIn(viewModelScope)
    val topRatedMovies = repository.getTopRatedMovies().cachedIn(viewModelScope)

    fun getUpcoming() {
        movieUpComing.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                if (hasInternetConnection(context)) {
                    val response = repository.getUpcomingMovie()
                    val data = response.body()
                    movieUpComing.postValue(Resource.Success(data!!))
                } else
                    movieUpComing.postValue(Resource.Error("No Internet Connection"))
            } catch (ex: Exception) {
                when (ex) {
                    is IOException -> movieUpComing.postValue(Resource.Error("Network Failure " + ex.localizedMessage))
                    else -> movieUpComing.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }

}