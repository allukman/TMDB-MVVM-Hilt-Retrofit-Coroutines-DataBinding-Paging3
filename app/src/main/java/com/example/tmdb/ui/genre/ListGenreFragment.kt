package com.example.tmdb.ui.genre

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.R
import com.example.tmdb.adapter.GenreAdapter
import com.example.tmdb.data.model.genre.GenreModel
import com.example.tmdb.databinding.FragmentListGenreBinding
import com.example.tmdb.ui.MainViewModel
import com.example.tmdb.util.Resource
import com.example.tmdb.util.SharedPreference
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListGenreFragment : Fragment() {
    private lateinit var bind: FragmentListGenreBinding
    private lateinit var sharedPref: SharedPreference
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var mAdapter: GenreAdapter
    private val list = ArrayList<GenreModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_list_genre, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPreference(view.context)

        initRecyclerView()
        obsViewModel()
    }

    private fun obsViewModel() {
        viewModel.getListAllGenre()
        observeUI()
    }

    private fun observeUI() {
        viewModel.listGenre.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    val data = it.data!!.genres
                    if (sharedPref.getGenre() == "") {
                        mAdapter.addList(data)
                    } else {
                        filterSelectedGenre(data)
                    }
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {
                }
            }

        }
    }

    private fun filterSelectedGenre(d: List<GenreModel>) {
        val sharedPref = sharedPref.getGenre().split(",")
        val compare: ArrayList<GenreModel> = arrayListOf()
        for (i in sharedPref) {
            compare.add(
                GenreModel(id = i.toInt(), "")
            )
        }
        val data = d.map { it ->
            GenreModel(
                id = it.id,
                name = it.name,
                isSelected = isSelected(it.id, compare)
            )
        }
        mAdapter.addList(data)
    }

    private fun isSelected(id: Int, data: ArrayList<GenreModel>): Boolean {
        var result = false
        for (i in data) {
            if(id == i.id) {
                result = true
            } else {
                continue
            }
        }
        return result
    }

    private fun saveFilter(d: ArrayList<GenreModel>) {
        var result = ""
        val data = d.filter {
            it.isSelected
        }
        for(i in data.indices) {
            result += if(i == data.lastIndex) {
                data[i].id.toString()
            } else {
                data[i].id.toString() + ","
            }
        }

        sharedPref.saveGenre(result)
    }

    private fun initRecyclerView() {
        mAdapter = GenreAdapter(list)
        bind.rvGenre.setHasFixedSize(true)
        bind.rvGenre.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        bind.rvGenre.adapter = mAdapter
        mAdapter.setOnItemClickCallback(object : GenreAdapter.OnItemClickCallback{
            override fun onClickItem(data: GenreModel) {
                saveFilter(list)
            }
        })
    }
}