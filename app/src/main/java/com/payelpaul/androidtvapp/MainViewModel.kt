package com.payelpaul.androidtvapp

import android.media.MediaDrm.LogMessage
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payelpaul.androidtvapp.api.NetworkResult
import com.payelpaul.androidtvapp.api.UserRepository
import com.payelpaul.androidtvapp.model.CastResponse
import com.payelpaul.androidtvapp.model.DetailResponse
import com.payelpaul.androidtvapp.model.MovieList
import com.payelpaul.androidtvapp.utils.Constants.API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {
    private val _sharedData = MutableLiveData<Int>()
    val sharedData: LiveData<Int> get() = _sharedData

    fun setSharedData(data: Int) {
        Log.d("log","$data")
        _sharedData.value = data
    }

    private val _notesLiveData = MutableLiveData<NetworkResult<CastResponse>>()
    val castLiveData get() = _notesLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<Pair<Boolean, String>>>()
    val statusLiveData get() = _statusLiveData



     fun createCastResponse(id: Int) {
       viewModelScope.launch {
           _statusLiveData.postValue(NetworkResult.Loading())
           val response = userRepository.getMovieCast(id,API_KEY)
           Log.e("JlvP",response.body().toString())
           _notesLiveData.postValue(NetworkResult.Success(response.body()))
       }
    }

    private val _sLiveData = MutableLiveData<NetworkResult<DetailResponse>>()
    val detailsLiveData :LiveData<NetworkResult<DetailResponse>> get() = _sLiveData
     fun getMovieDetails(id:Int){
        viewModelScope.launch {
            _statusLiveData.postValue(NetworkResult.Loading())
            val response = userRepository.getMovieDetails(id,API_KEY)
            _sLiveData.postValue(NetworkResult.Success(response.body()))

           // handleResponseDetailsMovie(response,"")
        }
    }



    private val _userLiveDataMovie = MutableLiveData<NetworkResult<MovieList>>()
    val movieLiveData:LiveData<NetworkResult<MovieList>> get() = _userLiveDataMovie

    fun getAllMovieList(page:Int){
        viewModelScope.launch {
            val  data = userRepository.getMovieList(API_KEY,page)
            Log.d("JAPAN=",data.toString())
            _userLiveDataMovie.postValue(NetworkResult.Success(data.body()))
            //handleResponseGetMovie(data,"Note Created")
        }
    }


    private fun handleResponse(response: Response<CastResponse>, message: String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success(Pair(true, message)))
            _notesLiveData.postValue(NetworkResult.Success(response.body()))
        } else {
            _statusLiveData.postValue(NetworkResult.Success(Pair(false, "Something went wrong")))
        }
    }



}