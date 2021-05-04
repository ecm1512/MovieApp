package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("release_date")
    val releaseDate: String,
    val overview: String
)