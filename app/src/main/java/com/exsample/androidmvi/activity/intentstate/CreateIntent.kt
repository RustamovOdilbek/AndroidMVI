package com.exsample.androidmvi.activity.intentstate

import com.exsample.androidmvi.model.Post

sealed class CreateIntent {
    data class CreatePost(val post: Post): CreateIntent()
}