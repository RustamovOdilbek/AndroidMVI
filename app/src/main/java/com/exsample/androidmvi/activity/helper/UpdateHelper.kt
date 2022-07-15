package com.exsample.androidmvi.activity.helper

import com.exsample.androidmvi.model.Post

interface UpdateHelper {

    suspend fun updatePost(id: Int, post: Post): Post
}