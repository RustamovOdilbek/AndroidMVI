package com.exsample.androidmvi.helper

import com.exsample.androidmvi.model.Post
import com.exsample.androidmvi.network.service.PostService
import retrofit2.Call
import java.util.*

class MainHelperIml(private val postService: PostService): MainHelper {
    override suspend fun allPost(): ArrayList<Post> {
        return postService.listPost()
    }

    override suspend fun deletePost(id: Int): Post {
        return postService.deletePost(id)
    }
}