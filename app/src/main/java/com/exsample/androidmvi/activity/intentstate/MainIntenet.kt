package com.exsample.androidmvi.activity.intentstate

sealed class MainIntenet {
    object AllPosts: MainIntenet()
    object DeletePost: MainIntenet()
}