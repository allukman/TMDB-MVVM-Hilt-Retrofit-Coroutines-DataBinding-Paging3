package com.example.tmdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.tmdb.R
import com.example.tmdb.data.model.movie.MovieModel
import com.example.tmdb.databinding.ItemViewpagerBinding

class ViewPagerAdapter(private val items: ArrayList<MovieModel>): RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    fun addList(list: List<MovieModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class ViewPagerViewHolder(val bind: ItemViewpagerBinding):RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        return ViewPagerViewHolder(
            DataBindingUtil.inflate(
                (LayoutInflater.from(parent.context)),
                R.layout.item_viewpager,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val item = items[position]
        val image = "${item.baseUrlImage}${item.backdropPath}"

        Glide.with(holder.itemView)
            .load(image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_error)
            .into(holder.bind.image)
    }


}