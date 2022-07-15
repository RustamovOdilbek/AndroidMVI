package com.exsample.androidmvi.repository

import com.exsample.androidmvi.activity.helper.CreateHelper
import com.exsample.androidmvi.model.Post

class CreateRepository(private val createHelper: CreateHelper) {


    suspend fun createPost(post: Post) = createHelper.createPost(post)

}