package com.example.movieapp.ui.movie_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.data.api.IMovieDB
import com.example.movieapp.data.api.MovieDBClient
import com.example.movieapp.data.repository.NetworkState
import com.example.movieapp.ui.MainActivity
import kotlinx.android.synthetic.main.activity_movie_list.*

class MovieListActivity : AppCompatActivity() {

    private lateinit var viewModel: ListMovieViewModel
    lateinit var movieRepository: MoviePagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        val apiService : IMovieDB = MovieDBClient.getClient()

        movieRepository = MoviePagedListRepository(apiService)

        viewModel = getViewModel()

        val movieAdapter = ListMoviePagedAdapter(this)

        val gridLayoutManager = GridLayoutManager(this,3)

        /*para ver el progress bar en una sola columna*/
        gridLayoutManager.spanSizeLookup = object:GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType: Int = movieAdapter.getItemViewType(position)
                if(viewType == movieAdapter.MOVIE_VIEW_TYPE) return 1   //MOVIE_VIEW_TYPE ocupara 1 espacio
                else return 3                                               // NETWORK_VIEW_TYPE ocupara los 3 espacios (progress bar)
            }
        }

        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = movieAdapter

        viewModel.moviePagedList.observe(this, Observer{
            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer{
            progress_bar_popular.visibility = if(viewModel.listIsEmpty() && it== NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility = if(viewModel.listIsEmpty() && it== NetworkState.ERROR) View.VISIBLE else View.GONE

            if(!viewModel.listIsEmpty()){
                movieAdapter.setNetworkState(it)
            }
        })
    }

    private fun getViewModel(): ListMovieViewModel{
        return ViewModelProviders.of(this,object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ListMovieViewModel(movieRepository) as T
            }
        })[ListMovieViewModel::class.java]
    }
}