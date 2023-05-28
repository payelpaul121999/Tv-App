package com.payelpaul.androidtvapp.model

import com.google.gson.annotations.SerializedName

data class Movie(

    @SerializedName("id")
    val id: Int,

    @SerializedName("backdrop_path")
    val backdrop_path: String = "",

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("title")
    val title: String?
)
