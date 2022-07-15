package com.exsample.androidmvi.activity.intentstate

import com.exsample.androidmvi.model.Post


sealed class UpdateIntent {

    data class UpdatePost(val id: Int, val post: Post): UpdateIntent()

}