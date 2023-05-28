package com.payelpaul.androidtvapp.api

import com.payelpaul.androidtvapp.model.CastResponse
import com.payelpaul.androidtvapp.model.DetailResponse
import com.payelpaul.androidtvapp.model.MovieList
import com.payelpaul.androidtvapp.model.TvShowList
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id")id: Int,
        @Query("api_key")apiKey: String
    ) : Response<DetailResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id")id: Int,
        @Query("api_key")apiKey: String
    ) : Response<CastResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey:String,
        @Query("page") page: Int
       ): Response<MovieList>

    @GET("tv/popular")
    suspend fun getPopularTvShow(
        @Query("api_key") apiKey:String,
        @Query("page") page: Int
    ): Response<TvShowList>

}