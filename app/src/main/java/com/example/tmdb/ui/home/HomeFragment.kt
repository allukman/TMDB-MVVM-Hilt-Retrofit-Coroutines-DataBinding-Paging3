package com.example.tmdb.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.tmdb.R
import com.example.tmdb.adapter.MovieAdapter
import com.example.tmdb.adapter.ViewPagerAdapter
import com.example.tmdb.data.model.movie.MovieModel
import com.example.tmdb.databinding.FragmentHomeBinding
import com.example.tmdb.ui.detail.DetailMovieActivity
import com.example.tmdb.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), MovieAdapter.OnItemClickListener {
    private lateinit var bind: FragmentHomeBinding
    private val popularAdapter = MovieAdapter(this)
    private val topRatedAdapter = MovieAdapter(this)
    private val list = ArrayList<MovieModel>()
    private var pos = 0
    private lateinit var upcomingAdapter: ViewPagerAdapter
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initViewPager()
        obsViewModel()
    }

    private fun obsViewModel() {
        viewModel.popularMovies.observe(viewLifecycleOwner) {
            popularAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        viewModel.topRatedMovies.observe(viewLifecycleOwner) {
            topRatedAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        viewModel.getUpcoming()
        observeUI()
    }

    private fun observeUI() {
        viewModel.movieUpComing.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    val data = it.data!!

                    val anu = mutableListOf<MovieModel>()
                    for (i in 0..4) {
                        anu += data.results[i]
                    }
                    upcomingAdapter.addList(anu)
                    setupIndicators()
                    setCurrentIndicator(0)
                }
                is Resource.Error -> {
                }
                is Resource.Loading -> {

                }
            }

        }
    }

    private fun initViewPager() {
        setupIndicators()
        setCurrentIndicator(pos)
        bind.upcomingViewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                pos = position
                setCurrentIndicator(position)
            }
        })
    }

    private fun setupIndicators(){
        val indicators = arrayOfNulls<ImageView>(upcomingAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8,0,8,0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(context)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            bind.container.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int){
        val childCount = bind.container.childCount
        for (i in 0 until childCount) {
            val imageView = bind.container[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }

    private fun initRecyclerView() {
        bind.rvPopularMovie.setHasFixedSize(true)
        bind.rvPopularMovie.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        bind.rvPopularMovie.adapter = popularAdapter
        popularAdapter.addLoadStateListener { loadState ->
            bind.shimmerPopular.isVisible = loadState.source.refresh is LoadState.Loading
            bind.rvPopularMovie.isVisible = loadState.source.refresh is LoadState.NotLoading
        }

        bind.rvTopRatedMovie.setHasFixedSize(true)
        bind.rvTopRatedMovie.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        bind.rvTopRatedMovie.adapter = topRatedAdapter
        topRatedAdapter.addLoadStateListener { loadState ->
            bind.shimmerToprated.isVisible = loadState.source.refresh is LoadState.Loading
            bind.rvTopRatedMovie.isVisible = loadState.source.refresh is LoadState.NotLoading
        }

        upcomingAdapter = ViewPagerAdapter(list)
        bind.upcomingViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        bind.upcomingViewpager.adapter = upcomingAdapter
    }

    override fun onItemClick(data: MovieModel) {
        val intent = Intent(activity, DetailMovieActivity::class.java)
        intent.putExtra("movie", data)
        startActivity(intent)
    }

}