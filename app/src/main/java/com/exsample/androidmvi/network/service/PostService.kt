package com.exsample.androidmvi.network.service

import com.exsample.androidmvi.model.Post
import retrofit2.Call
import retrofit2.http.*
import java.util.*


interface PostService {

    @Headers(
        "Content-type:application/json"
    )

    @GET("posts")
    suspend fun allPosts(): ArrayList<Post>

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: Int): Post

    @Headers(
        "Content-type:application/json"
    )
    @POST("posts")
    suspend fun createPost(@Body post: Post): Post


    @PUT("posts/{id}")
    suspend fun updatePost(@Path("id") id: Int, @Body post: Post): Post



}