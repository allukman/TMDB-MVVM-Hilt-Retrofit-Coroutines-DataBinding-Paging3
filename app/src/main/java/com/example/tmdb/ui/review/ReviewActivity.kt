package com.example.tmdb.ui.review

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.tmdb.R
import com.example.tmdb.adapter.GenreAdapter
import com.example.tmdb.adapter.ReviewAdapter
import com.example.tmdb.data.model.movie.MovieModel
import com.example.tmdb.data.model.reviews.ReviewModel
import com.example.tmdb.data.model.reviews.ReviewsResponse
import com.example.tmdb.databinding.ActivityReviewBinding
import com.google.gson.Gson

class ReviewActivity : AppCompatActivity() {
    private lateinit var bind: ActivityReviewBinding
    private lateinit var mAdapter: ReviewAdapter
    private val list = ArrayList<ReviewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_review)

        val data = intent.getSerializableExtra("review") as ReviewsResponse

        initRecyclerView()
        mAdapter.addList(data.results)
        Log.d("ANUUUU", Gson().toJson(data))
    }

    private fun initRecyclerView() {
        mAdapter = ReviewAdapter(list)
        bind.rvReview.setHasFixedSize(true)
        bind.rvReview.adapter = mAdapter
    }
}