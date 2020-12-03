package com.mehrsoft.task_.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getPosts() = apiService.getPosts()

    suspend fun getPostById(id: Int) = apiService.getPostsById(id)
    suspend fun getPhotos(id: Int) = apiService.getPhotos(id)


}