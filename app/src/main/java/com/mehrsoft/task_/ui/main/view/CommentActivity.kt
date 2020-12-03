package com.mehrsoft.task_.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviemvvm.slider.SliderPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.mehrsoft.task_.R
import com.mehrsoft.task_.data.api.ApiHelper
import com.mehrsoft.task_.data.api.RetrofitBuilder
import com.mehrsoft.task_.data.model.PhotosItem
import com.mehrsoft.task_.data.model.comments.CommentItem
import com.mehrsoft.task_.ui.base.ViewModelFactory
import com.mehrsoft.task_.ui.main.adapter.CommentAdapter
import com.mehrsoft.task_.ui.main.viewmodel.MainViewModel
import com.mehrsoft.task_.utils.Status
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_comment.*
import java.util.concurrent.TimeUnit

class CommentActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    private lateinit var adapter: CommentAdapter

    private val INTERVAL_DURATION = 5000L
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        val extras = intent.extras!!.getInt("id", 0)


        setupViewModel()
        setupUI()
        getPostById(extras)
        getPhotos(extras)
    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }


    private fun retrieveList(comment: List<CommentItem>) {
        adapter.apply {
            addComment(comment)
            notifyDataSetChanged()
        }
    }

    private fun getPostById(id: Int) {
        viewModel.getPostsById(id = id).observe(this, Observer {

            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        c_recyclerView.visibility = View.VISIBLE
                        c_progressBar.visibility = View.GONE
                        resource.data?.let { comment -> retrieveList(comment) }
                    }
                    Status.ERROR -> {
                        c_recyclerView.visibility = View.VISIBLE
                        c_progressBar.visibility = View.GONE
                        Toast.makeText(this, "error " + it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        c_progressBar.visibility = View.VISIBLE
                        c_recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun getPhotos(id: Int) {
        viewModel.getPhotos(id = id).observe(this, Observer {

            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        c_recyclerView.visibility = View.VISIBLE
                        c_progressBar.visibility = View.GONE
                        resource.data?.let { res ->

                            slideShow(res)


                        }
                    }
                    Status.ERROR -> {
                        //   c_recyclerView.visibility = View.VISIBLE
                        // c_progressBar.visibility = View.GONE
                        Toast.makeText(this, "error " + it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        c_progressBar.visibility = View.VISIBLE
                        c_recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun slideShow(res: List<PhotosItem>): Boolean {
        val indicator =
            findViewById<TabLayout>(R.id.indicator)

        val sliderPagerAdapter = SliderPagerAdapter(this, res)

        viewPager.adapter = sliderPagerAdapter


        val subscribe =
            Observable.interval(INTERVAL_DURATION, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (viewPager.currentItem < res.size - 1) {
                        viewPager.currentItem = viewPager.currentItem + 1
                    } else {
                        viewPager.currentItem = 0
                    }
                }
        indicator.setupWithViewPager(viewPager, true)
        return compositeDisposable.add(subscribe)


    }


    private fun setupUI() {
        c_recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CommentAdapter(arrayListOf())
        c_recyclerView.addItemDecoration(
            DividerItemDecoration(
                c_recyclerView.context,
                (c_recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        c_recyclerView.adapter = adapter
    }
}