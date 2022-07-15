package com.exsample.androidmvi.activity.helper

import com.exsample.androidmvi.model.Post

interface CreateHelper {

    suspend fun createPost(post: Post): Post
}