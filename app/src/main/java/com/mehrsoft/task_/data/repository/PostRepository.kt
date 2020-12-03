package com.mehrsoft.task_.data.repository

import com.mehrsoft.task_.data.api.ApiHelper

class PostRepository(private val apiHelper: ApiHelper) {
    suspend fun getPosts() = apiHelper.getPosts()
    suspend fun getPostId(id: Int) = apiHelper.getPostById(id)
    suspend fun getPhotos(id: Int) = apiHelper.getPhotos(id)


}