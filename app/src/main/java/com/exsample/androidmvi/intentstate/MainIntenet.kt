package com.exsample.androidmvi.intentstate

sealed class MainIntenet {
    object AllPosts: MainIntenet()
    object DeletePost: MainIntenet()
}