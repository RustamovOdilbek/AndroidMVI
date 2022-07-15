package com.exsample.androidmvi.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.exsample.androidmvi.activity.helper.UpdateHelper
import com.exsample.androidmvi.repository.UpdateRepository


class UpdateViewModelFactory(private val updateHelper: UpdateHelper): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateViewModel::class.java)) {
            return UpdateViewModel(UpdateRepository(updateHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}