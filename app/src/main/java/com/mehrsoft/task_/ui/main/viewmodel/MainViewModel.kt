package com.mehrsoft.task_.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mehrsoft.task_.data.repository.PostRepository
import com.mehrsoft.task_.utils.Resource

import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MainViewModel (private val repository: PostRepository):ViewModel(){

    fun getPosts()= liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getPosts()))
        }catch (ex:Exception){
            emit(Resource.error(data = null,message = ex.message ?: "Error Occurred!"))
        }

    }


    fun getPostsById(id:Int)=

        liveData(Dispatchers.IO) {

            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = repository.getPostId(id)))
            } catch (ex: Exception) {
                emit(Resource.error(data = null, message = ex.message ?: "Error Occurred!"))
            }
    }



    fun getPhotos(id:Int)=

        liveData(Dispatchers.IO) {

            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = repository.getPhotos(id)))
            } catch (ex: Exception) {
                emit(Resource.error(data = null, message = ex.message ?: "Error Occurred!"))
            }
        }
}