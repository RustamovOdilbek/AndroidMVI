package com.exsample.androidmvi.activity.intentstate

import com.exsample.androidmvi.model.Post
import java.util.*

sealed class MainState {
    object Init: MainState()
    object Loading: MainState()

    data class AllPosts(val posts: ArrayList<Post>): MainState()
    data class DletePost(val  post: Post): MainState()

    data class Error(val error: String): MainState()
}