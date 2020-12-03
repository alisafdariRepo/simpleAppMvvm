package com.mehrsoft.task_.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mehrsoft.task_.R
import com.mehrsoft.task_.data.api.ApiHelper
import com.mehrsoft.task_.data.api.RetrofitBuilder
import com.mehrsoft.task_.data.model.PostsItem
import com.mehrsoft.task_.ui.base.ViewModelFactory
import com.mehrsoft.task_.ui.main.adapter.PostAdapter
import com.mehrsoft.task_.ui.main.viewmodel.MainViewModel
import com.mehrsoft.task_.utils.Status
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupUI()
        getPost()


    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }


    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PostAdapter(arrayListOf(),this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }


    private fun getPost() {
        viewModel.getPosts().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { users -> retrieveList(users) }
                    }
                    Status.ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }
    private fun getPostById() {
        viewModel.getPostsById(1).observe(this, Observer {

            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { comments -> comments.forEach { c ->   Toast.makeText(this, ""+c.name, Toast.LENGTH_LONG).show() } }
                    }
                    Status.ERROR -> {
                      //  recyclerView.visibility = View.VISIBLE
                        //progressBar.visibility = View.GONE
                        Toast.makeText(this, "error "+it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        //progressBar.visibility = View.VISIBLE
                        //recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }


    private fun retrieveList(posts: List<PostsItem>) {
        adapter.apply {
            addUsers(posts)
            notifyDataSetChanged()
        }
    }
}