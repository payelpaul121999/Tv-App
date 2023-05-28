package com.payelpaul.androidtvapp.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.payelpaul.androidtvapp.model.CastResponse
import com.payelpaul.androidtvapp.model.DetailResponse
import com.payelpaul.androidtvapp.utils.Constants.API_KEY
import javax.inject.Inject

class UserRepository @Inject constructor(private val service: UserAPI) {


    suspend fun getMovieCast(id: Int, apiKey: String) = service.getMovieCast(id,apiKey)

    suspend fun getMovieDetails(id: Int, apiKey: String) = service.getMovieDetails(id,apiKey)


    suspend fun getMovieList(apiKey: String,page:Int) = service.getPopularMovies(apiKey,page)
    suspend fun getTvShowList(apiKey: String,page:Int) = service.getPopularTvShow(apiKey,page)
}