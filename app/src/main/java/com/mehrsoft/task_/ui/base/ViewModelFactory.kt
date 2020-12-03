package com.mehrsoft.task_.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mehrsoft.task_.data.api.ApiHelper
import com.mehrsoft.task_.data.repository.PostRepository
import com.mehrsoft.task_.ui.main.viewmodel.MainViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(PostRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }



}