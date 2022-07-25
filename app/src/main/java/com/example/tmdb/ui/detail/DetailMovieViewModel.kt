package com.example.tmdb.ui.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.model.detail.DetailMovieModel
import com.example.tmdb.data.model.detail.VideoResponse
import com.example.tmdb.data.model.movie.MovieResponse
import com.example.tmdb.data.model.reviews.ReviewsResponse
import com.example.tmdb.data.remote.MovieRepository
import com.example.tmdb.util.Helper
import com.example.tmdb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val repository: MovieRepository,
    @ApplicationContext private val context: Context
): ViewModel() {
    val movie: MutableLiveData<Resource<DetailMovieModel>> = MutableLiveData()
    val trailer: MutableLiveData<Resource<VideoResponse>> = MutableLiveData()
    val reviews: MutableLiveData<Resource<ReviewsResponse>> = MutableLiveData()

    fun getDetailMovie(movieId: Int) {
        movie.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                if (Helper.hasInternetConnection(context)) {
                    val response = repository.getDetailMovie(movieId)
                    val data = response.body()
                    movie.postValue(Resource.Success(data!!))
                } else
                    movie.postValue(Resource.Error("No Internet Connection"))
            } catch (ex: Exception) {
                when (ex) {
                    is IOException -> movie.postValue(Resource.Error("Network Failure " + ex.localizedMessage))
                    else -> movie.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }

    fun getTrailerMovie(movieId: Int) {
        trailer.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                if (Helper.hasInternetConnection(context)) {
                    val response = repository.getTrailerMovie(movieId)
                    val data = response.body()
                    trailer.postValue(Resource.Success(data!!))
                } else
                    trailer.postValue(Resource.Error("No Internet Connection"))
            } catch (ex: Exception) {
                when (ex) {
                    is IOException -> trailer.postValue(Resource.Error("Network Failure " + ex.localizedMessage))
                    else -> trailer.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }

    fun getReviewsMovie(movieId: Int) {
        reviews.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                if (Helper.hasInternetConnection(context)) {
                    val response = repository.getReviewsMovie(movieId)
                    val data = response.body()
                    reviews.postValue(Resource.Success(data!!))
                } else
                    reviews.postValue(Resource.Error("No Internet Connection"))
            } catch (ex: Exception) {
                when (ex) {
                    is IOException -> reviews.postValue(Resource.Error("Network Failure " + ex.localizedMessage))
                    else -> reviews.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }

}