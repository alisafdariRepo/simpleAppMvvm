package com.mehrsoft.task_.data.api


import com.mehrsoft.task_.data.model.PhotosItem
import com.mehrsoft.task_.data.model.PostsItem
import com.mehrsoft.task_.data.model.comments.CommentItem
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService{
    @GET("posts")
    suspend fun getPosts():List<PostsItem>

    @GET("posts/{id}/comments")
    suspend fun getPostsById(@Path("id") id: Int):List<CommentItem>

    @GET("posts/{id}/photos")
    suspend fun getPhotos(@Path("id") id: Int):List<PhotosItem>



}