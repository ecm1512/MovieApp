package com.example.movieapp.data.api

import com.example.movieapp.data.model.MovieDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface IMovieDB {
    @GET("movie/{movie_id}")

    // Single es tipo de observable en rxjava
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>
}


