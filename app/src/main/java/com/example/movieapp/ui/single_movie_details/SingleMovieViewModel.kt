package com.example.movieapp.ui.single_movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.model.MovieDetails
import com.example.movieapp.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(private val movieRepository: MovieDetailsRepository, movieId: Int): ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val movieDetails: LiveData<MovieDetails> by lazy { //solo se tendra el detalle de la pelicula cuando se solicite no cuando se instancia este view model, se usa para un mejor performance
        movieRepository.fetchSingleMovieDetails(compositeDisposable,movieId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    //desechar nuestro compositedisposable cuando se cierre el activity
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}