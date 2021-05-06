package com.example.movieapp.ui.movie_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class ListMovieViewModel(private val movieRepository: MoviePagedListRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList : LiveData<PagedList<Movie>> by lazy{
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy{
        movieRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean{
        return moviePagedList.value?.isEmpty() ?:true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}