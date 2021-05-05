package com.example.movieapp.ui.single_movie_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.api.IMovieDB
import com.example.movieapp.data.api.MovieDBClient
import com.example.movieapp.data.api.POSTER_BASE_URL
import com.example.movieapp.data.model.MovieDetails
import com.example.movieapp.data.repository.NetworkState
import kotlinx.android.synthetic.main.activity_single_movie.*


class SingleMovie : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val movieId: Int = intent.getIntExtra("id",1)

        val apiService: IMovieDB = MovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer{
            bindUI(it)
        })

        viewModel.networkState.observe(this,Observer{
            progress_bar.visibility = if(it== NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if(it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
    }

    fun bindUI(it: MovieDetails){
        movie_title.text = it.title
        vote_average.text = it.voteAverage.toString()
        release_date.text = it.releaseDate
        overview.text = it.overview

        val moviePosterURL: String = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
                .load(moviePosterURL)
                .into(iv_movie_poster);
    }

    private fun getViewModel(movieId: Int): SingleMovieViewModel{
        return ViewModelProviders.of(this,object: ViewModelProvider.Factory{
            override fun<T: ViewModel?> create(modelClass: Class<T>): T{
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieRepository,movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}