package com.exsample.androidmvi.helper

import com.exsample.androidmvi.model.Post
import java.util.*

interface MainHelper {

    suspend fun allPost(): ArrayList<Post>

    suspend fun deletePost(id: Int): Post

}