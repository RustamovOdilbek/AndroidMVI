package com.exsample.androidmvi.repository

import com.exsample.androidmvi.helper.MainHelper

class PostRepository(private val mainHelper: MainHelper) {

    suspend fun allPosts() = mainHelper.allPost()

    suspend fun deletePost(id: Int) = mainHelper.deletePost(id)

}