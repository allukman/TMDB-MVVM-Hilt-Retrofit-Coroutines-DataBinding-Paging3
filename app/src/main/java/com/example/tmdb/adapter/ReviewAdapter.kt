package com.example.tmdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.tmdb.R
import com.example.tmdb.data.model.reviews.ReviewModel
import com.example.tmdb.databinding.ItemReviewsBinding

class ReviewAdapter(private val items: ArrayList<ReviewModel>): RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    fun addList(list: List<ReviewModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class ReviewViewHolder(val bind: ItemReviewsBinding): RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(
            DataBindingUtil.inflate(
                (LayoutInflater.from(parent.context)),
                R.layout.item_reviews,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val item = items[position]

        holder.bind.tvName.text = item.author
        holder.bind.tvDate.text = item.created_at.split("T")[0]
        holder.bind.tvContent.text = item.content
        holder.bind.score.text = item.author_details.rating.toString()

        if (item.author_details.avatar_path != null) {
            val authorPath = item.author_details.avatar_path.split("https")
            val path = if (authorPath.size <= 1) {
                "${item.baseUrlImage}${item.author_details.avatar_path}"
            } else {
                item.author_details.avatar_path.drop(1)
            }

            Glide.with(holder.itemView)
                .load(path)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error)
                .into(holder.bind.ivProfile)
        }
    }


}