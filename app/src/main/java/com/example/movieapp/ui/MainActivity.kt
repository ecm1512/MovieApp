package com.example.movieapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.movieapp.R
import com.example.movieapp.config.Setting
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.ui.login.UserViewModel
import com.example.movieapp.ui.single_movie_details.SingleMovie
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        /*btn.setOnClickListener{
            val intent = Intent(this, SingleMovie::class.java)
            intent.putExtra("id",299534)
            this.startActivity(intent)
        }*/

        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainBinding.userModel = userViewModel
        mainBinding.executePendingBindings()

        observer()
    }

    private fun observer() {
        userViewModel.activityMutable.observe(this, Observer {
            val intent = Intent(this, SingleMovie::class.java)
            intent.putExtra("id",299534)
            this.startActivity(intent)
        })

        userViewModel.messageMutable.observe(this, Observer {
            Toast.makeText(this, getString(it), Toast.LENGTH_SHORT).show()
        })
    }
}