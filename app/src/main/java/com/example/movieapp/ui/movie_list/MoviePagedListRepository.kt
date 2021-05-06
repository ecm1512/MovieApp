package com.example.movieapp.ui.movie_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movieapp.data.api.IMovieDB
import com.example.movieapp.data.api.POST_PER_PAGE
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.repository.MovieDataSource
import com.example.movieapp.data.repository.MovieDataSourceFactory
import com.example.movieapp.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository(private val apiService: IMovieDB) {
    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>>{
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory,config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(moviesDataSourceFactory.moviesLiveDataSource,MovieDataSource::networkState)
    }
}