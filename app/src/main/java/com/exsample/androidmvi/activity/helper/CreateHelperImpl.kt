package com.exsample.androidmvi.activity.helper

import com.exsample.androidmvi.model.Post
import com.exsample.androidmvi.network.service.PostService

class CreateHelperImpl(private val postService: PostService): CreateHelper {

    override suspend fun createPost(post: Post): Post {
        return postService.createPost(post)
    }
}