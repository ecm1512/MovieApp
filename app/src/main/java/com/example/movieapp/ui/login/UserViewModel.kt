package com.example.movieapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.R

class UserViewModel : ViewModel() {

    val activityMutable: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val messageMutable: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    var user = ""
    var password = ""

    fun loginUser() {
        if (user == "Admin" && password == "Password*123") {
            activityMutable.value = R.string.success_user_message
        } else {
            messageMutable.value = R.string.verify_user_message
        }
    }
}
