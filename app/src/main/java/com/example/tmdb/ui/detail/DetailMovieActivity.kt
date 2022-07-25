package com.example.tmdb.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.tmdb.R
import com.example.tmdb.adapter.GenreMovieAdapter
import com.example.tmdb.data.model.detail.DetailMovieModel
import com.example.tmdb.data.model.genre.GenreModel
import com.example.tmdb.data.model.movie.MovieModel
import com.example.tmdb.databinding.ActivityDetailMovieBinding
import com.example.tmdb.ui.review.ReviewActivity
import com.example.tmdb.util.Resource
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.gson.Gson
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {
    private lateinit var bind: ActivityDetailMovieBinding
    private val viewModel by viewModels<DetailMovieViewModel>()
    private lateinit var adapterGenres: GenreMovieAdapter
    private val list = ArrayList<GenreModel>()
    private var linkTrailer: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_detail_movie)

        val data = intent.getSerializableExtra("movie") as MovieModel

        setGenresRecyclerView()
        obsViewModel(data.id)

    }

    private fun obsViewModel(id: Int) {
        viewModel.getDetailMovie(id)
        viewModel.getTrailerMovie(id)
        viewModel.getReviewsMovie(id)
        observeUI()
    }

    private fun observeUI() {
        viewModel.movie.observe(this) {
            when (it) {
                is Resource.Success -> {
                    bind.progressBar.visibility = View.GONE
                    initData(it.data!!)
                    adapterGenres.addList(it.data.genres)
                }
                is Resource.Error -> {
                    bind.progressBar.visibility = View.GONE
                }
                is Resource.Loading -> {
                    bind.progressBar.visibility = View.VISIBLE
                }
            }
        }

        viewModel.trailer.observe(this) {
            when (it) {
                is Resource.Success -> {
                    val size = it.data?.results?.size
                    if(size!! >= 1) {
                        it.data.results[0].let { trailer ->
                            linkTrailer = trailer.key
                        }
                        startTrailer()
                        bind.tvTextTrailer.visibility = View.VISIBLE
                        bind.youtubePlayerView.visibility = View.VISIBLE
                    } else {
                        bind.tvTextTrailer.visibility = View.GONE
                        bind.youtubePlayerView.visibility = View.GONE
                    }

                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }

        viewModel.reviews.observe(this) {
            when (it) {
                is Resource.Success -> {
                    val data = it.data!!
                    Log.d("ANUUUU_DATA", Gson().toJson(data))
                    val totalReview = data.results.size
                    if(totalReview > 0) {
                        bind.btnReview.visibility = View.VISIBLE
                        bind.btnReview.text = "Review($totalReview)"
                        bind.btnReview.setOnClickListener {
                            val intent = Intent(this, ReviewActivity::class.java)
                            intent.putExtra("review", data)
                            startActivity(intent)
                        }
                    } else {
                        bind.btnReview.visibility = View.GONE
                    }
                }
                is Resource.Error -> {
                    bind.btnReview.visibility = View.GONE
                }
                is Resource.Loading -> {
                    bind.btnReview.visibility = View.GONE
                }
            }
        }
    }

    private fun initData(d: DetailMovieModel) {
        val hours = d.runtime / 60
        val minutes = d.runtime % 60
        val time = hours.toString() + "h " + minutes.toString() + "m"
        val year = d.release_date.split("-")[0]

        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("USD")

        bind.tvTitle.text = d.title
        bind.tvTime.text = time
        bind.tvYear.text = "($year)"
        bind.tvTagline.text = d.tagline
        bind.tvOverview.text = d.overview
        bind.tvOriginalTitle.text = d.original_title
        bind.tvStatus.text = d.status
        bind.tvOriginalLanguage.text = d.original_language
        bind.tvBudget.text = format.format(d.budget)
        bind.tvRevenue.text = format.format(d.revenue)

        Glide.with(this)
            .load("${d.baseUrlImage}${d.backdrop_path}")
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_error)
            .into(bind.ivBackdrop)

        Glide.with(this)
            .load("${d.baseUrlImage}${d.poster_path}")
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_error)
            .into(bind.ivPoster)
    }

    private fun startTrailer() {
        if (linkTrailer == null) {
            bind.youtubePlayerView.visibility = View.GONE
        } else {
            bind.youtubePlayerView.visibility = View.VISIBLE
            bind.youtubePlayerView.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(linkTrailer!!, 0f)
                }
            })
        }
    }

    private fun setGenresRecyclerView() {
        bind.rvGenresMovie.isNestedScrollingEnabled = false
        adapterGenres = GenreMovieAdapter(list)
        bind.rvGenresMovie.adapter = adapterGenres

        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        bind.rvGenresMovie.layoutManager = layoutManager
    }

    override fun onDestroy() {
        super.onDestroy()
        bind.youtubePlayerView.release()
    }
}