package com.esgi.steamstorereactive.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esgi.steamstorereactive.R
import com.esgi.steamstorereactive.model.Review
import com.esgi.steamstorereactive.recyclerview.holder.ReviewViewHolder

class ReviewListAdapter(val items: List<Review>) : RecyclerView.Adapter<ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.game_review_cell, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.update(
            items[position]
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }
}