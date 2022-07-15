package com.exsample.androidmvi.activity.intentstate

import com.exsample.androidmvi.model.Post


sealed class UpdateState {
    object Init : UpdateState()
    object Loading : UpdateState()

    data class UpdatePost(val id: Int, val post: Post) : UpdateState()


    data class Error(val error: String) : UpdateState()


}