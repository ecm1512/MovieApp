package com.example.movieapp.data.api

import com.example.movieapp.data.model.MovieDetails
import com.example.movieapp.data.model.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IMovieDB {
    @GET("movie/upcoming")
    fun getUpcomingMovie(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/{movie_id}")
    // Single es tipo de observable en rxjava
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>
}


