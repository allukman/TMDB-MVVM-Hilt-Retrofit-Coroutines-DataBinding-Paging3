package com.example.tmdb.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.model.genre.GenreResponse
import com.example.tmdb.data.remote.MovieRepository
import com.example.tmdb.util.Helper
import com.example.tmdb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MovieRepository,
    @ApplicationContext private val context: Context
): ViewModel() {
    val listGenre : MutableLiveData<Resource<GenreResponse>> = MutableLiveData()

    fun getListAllGenre() {
        listGenre.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                if (Helper.hasInternetConnection(context)) {
                    val response = repository.getListGenre()
                    val data = response.body()
                    listGenre.postValue(Resource.Success(data!!))
                } else
                    listGenre.postValue(Resource.Error("No Internet Connection"))
            } catch (ex: Exception) {
                when (ex) {
                    is IOException -> listGenre.postValue(Resource.Error("Network Failure " + ex.localizedMessage))
                    else -> listGenre.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }
}