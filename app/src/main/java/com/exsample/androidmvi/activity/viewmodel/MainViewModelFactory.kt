package com.exsample.androidmvi.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.exsample.androidmvi.activity.helper.MainHelper
import com.exsample.androidmvi.repository.PostRepository

class MainViewModelFactory(private val mainHelper: MainHelper): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(PostRepository((mainHelper))) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}