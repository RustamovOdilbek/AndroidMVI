package com.exsample.androidmvi.activity.helper

import com.exsample.androidmvi.model.Post
import com.exsample.androidmvi.network.service.PostService
import java.util.ArrayList

class UpdateHelperImpl(private val postService: PostService): UpdateHelper {
    override suspend fun updatePost(id: Int, post: Post): Post {
        return postService.updatePost(id, post)
    }

}