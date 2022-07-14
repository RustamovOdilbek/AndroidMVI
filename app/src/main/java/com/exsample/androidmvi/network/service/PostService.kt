package com.exsample.androidmvi.network.service

import com.exsample.androidmvi.model.Post
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import java.util.*


interface PostService {

    @Headers(
        "Content-type:application/json"
    )

    @GET("posts")
    suspend fun listPost(): ArrayList<Post>

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: Int): Post

}