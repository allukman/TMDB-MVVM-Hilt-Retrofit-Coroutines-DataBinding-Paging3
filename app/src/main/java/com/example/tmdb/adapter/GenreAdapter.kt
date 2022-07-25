package com.example.tmdb.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.R
import com.example.tmdb.data.model.genre.GenreModel
import com.example.tmdb.databinding.ItemGenreBinding

class GenreAdapter(private val items: ArrayList<GenreModel>): RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addList(list: List<GenreModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class GenreViewHolder(val bind: ItemGenreBinding):RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            DataBindingUtil.inflate(
                (LayoutInflater.from(parent.context)),
                R.layout.item_genre,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val item = items[position]

        holder.bind.textGenre.text = item.name

        if(item.isSelected) {
            holder.bind.container.setBackgroundColor(Color.BLACK)
            holder.bind.textGenre.setTextColor(Color.WHITE)
        } else {
            holder.bind.container.setBackgroundColor(Color.WHITE)
            holder.bind.textGenre.setTextColor(Color.BLACK)
        }

        holder.bind.container.setOnClickListener {
            item.isSelected = !item.isSelected
            notifyItemChanged(position)

            onItemClickCallback.onClickItem(item)
        }
    }

    interface OnItemClickCallback {
        fun onClickItem(data: GenreModel)
    }
}