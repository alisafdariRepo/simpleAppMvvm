package com.mehrsoft.task_.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.moviemvvm.slider.LocalSliderPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.mehrsoft.task_.R
import com.mehrsoft.task_.data.api.ApiHelper
import com.mehrsoft.task_.data.api.RetrofitBuilder
import com.mehrsoft.task_.data.model.PostsItem
import com.mehrsoft.task_.ui.base.ViewModelFactory
import com.mehrsoft.task_.ui.main.adapter.PostAdapter
import com.mehrsoft.task_.ui.main.viewmodel.MainViewModel
import com.mehrsoft.task_.utils.Status
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: PostAdapter


    private val INTERVAL_DURATION = 5000L
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupUI()
        getPost()

        slideShow()


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


    private fun slideShow(): Boolean {
        val indicator =
            findViewById<TabLayout>(R.id.slide_indicator)
        val viewPager =
            findViewById<ViewPager>(R.id.slide_viewPager)

        val slide_1 = com.mehrsoft.task_.slider.Slide(R.drawable.slide1)
        val slide_2 = com.mehrsoft.task_.slider.Slide(R.drawable.slide2)
        val slide_3 = com.mehrsoft.task_.slider.Slide(R.drawable.slide1)
        val slide_4 = com.mehrsoft.task_.slider.Slide(R.drawable.slide2)

        val slideArray = ArrayList<com.mehrsoft.task_.slider.Slide>()

        slideArray.add(slide_1)
        slideArray.add(slide_2)
        slideArray.add(slide_3)
        slideArray.add(slide_4)
        val sliderPagerAdapter = LocalSliderPagerAdapter(this, slideArray)
        viewPager.adapter = sliderPagerAdapter
        indicator.setupWithViewPager(viewPager)


        val subscribe =
            Observable.interval(INTERVAL_DURATION, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (viewPager.currentItem < slideArray.size - 1) {
                        viewPager.currentItem = viewPager.currentItem + 1
                    } else {
                        viewPager.currentItem = 0
                    }
                }
        return compositeDisposable.add(subscribe)


    }


    private fun retrieveList(posts: List<PostsItem>) {
        adapter.apply {
            addUsers(posts)
            notifyDataSetChanged()
        }
    }
}