package com.example.tmdb.ui.genre

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tmdb.R
import com.example.tmdb.adapter.MovieAdapter
import com.example.tmdb.data.model.movie.MovieModel
import com.example.tmdb.databinding.FragmentGenreBinding
import com.example.tmdb.ui.detail.DetailMovieActivity
import com.example.tmdb.util.SharedPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreFragment : Fragment(), MovieAdapter.OnItemClickListener {
    private lateinit var bind: FragmentGenreBinding
    private lateinit var sharedPref: SharedPreference
    private val viewModel by viewModels<GenreViewModel>()
    private val mAdapter = MovieAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_genre, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPreference(view.context)

        initRecyclerView()
        obsViewModel(sharedPref.getGenre())
    }

    private fun obsViewModel(genre: String) {
        if (genre == "") {
            bind.filterEmpty.visibility = View.VISIBLE
            bind.rvMovie.visibility = View.GONE
        } else {
            bind.filterEmpty.visibility = View.GONE
            bind.rvMovie.visibility = View.VISIBLE
            viewModel.filterByGenre(genre)
            viewModel.getMovieByGenre.observe(viewLifecycleOwner) {
                mAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }
    }

    private fun initRecyclerView() {
        bind.rvMovie.adapter = mAdapter
    }

    override fun onItemClick(data: MovieModel) {
        val intent = Intent(activity, DetailMovieActivity::class.java)
        intent.putExtra("movie", data)
        startActivity(intent)
    }

}