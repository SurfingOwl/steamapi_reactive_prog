package com.esgi.steamstorereactive.recyclerview.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esgi.steamstorereactive.R
import com.esgi.steamstorereactive.model.Review

class ReviewViewHolder(v: View) : RecyclerView.ViewHolder(v)  {

    val username = v.findViewById<TextView>(R.id.username)
    val rate = v.findViewById<ImageView>(R.id.rate)
    val content = v.findViewById<TextView>(R.id.review)

    fun update(review: Review) {
        username.text = review.author.steamid.toString()
        if (review.voted_up) rate.setImageResource(R.drawable.ic_baseline_thumb_up_24) else rate.setImageResource(R.drawable.ic_baseline_thumb_down_24)
        content.text = review.review
    }

}