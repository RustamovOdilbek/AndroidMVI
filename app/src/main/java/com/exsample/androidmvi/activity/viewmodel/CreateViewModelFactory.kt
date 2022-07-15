package com.exsample.androidmvi.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.exsample.androidmvi.activity.helper.CreateHelper
import com.exsample.androidmvi.repository.CreateRepository

class CreateViewModelFactory(private val mainHelper: CreateHelper): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateViewModel::class.java)) {
            return CreateViewModel(CreateRepository(mainHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}