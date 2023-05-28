package com.payelpaul.androidtvapp.model

import com.google.gson.annotations.SerializedName

data class MovieList(

    @SerializedName("results")
    val results: ArrayList<Movie>

)
