package com.example.movieapp.ui.single_movie_details

import androidx.lifecycle.LiveData
import com.example.movieapp.data.api.IMovieDB
import com.example.movieapp.data.model.MovieDetails
import com.example.movieapp.data.repository.MovieDetailsNetworkDataSource
import com.example.movieapp.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository (private val apiService: IMovieDB){
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable, movieId: Int): LiveData<MovieDetails>{
        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse
    }

    // cachea la data en el local storage
    fun getMovieDetailsNetworkState(): LiveData<NetworkState>{
        return movieDetailsNetworkDataSource.networkState
    }
}