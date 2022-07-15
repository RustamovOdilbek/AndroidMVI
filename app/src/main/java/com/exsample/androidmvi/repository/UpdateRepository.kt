package com.exsample.androidmvi.repository

import com.exsample.androidmvi.activity.helper.UpdateHelper
import com.exsample.androidmvi.model.Post

class UpdateRepository(private val updateHelper: UpdateHelper) {

    suspend fun updatePost(id: Int, post: Post) = updateHelper.updatePost(id, post)

}