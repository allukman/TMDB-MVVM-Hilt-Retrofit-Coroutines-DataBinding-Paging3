package com.example.tmdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.R
import com.example.tmdb.data.model.genre.GenreModel
import com.example.tmdb.databinding.ItemGenresBinding

class GenreMovieAdapter(private val items: ArrayList<GenreModel>): RecyclerView.Adapter<GenreMovieAdapter.GenresHolder>() {

    fun addList(list: List<GenreModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class GenresHolder(val binding: ItemGenresBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresHolder {
        return GenresHolder(
            DataBindingUtil.inflate(
                (LayoutInflater.from(parent.context)),
                R.layout.item_genres,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GenresHolder, position: Int) {
        val item = items[position]
        val genreName = item.name

        holder.binding.tvGenre.text = "$genreName"
    }
}