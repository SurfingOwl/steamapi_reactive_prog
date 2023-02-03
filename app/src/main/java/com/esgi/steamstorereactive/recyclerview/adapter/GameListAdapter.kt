package com.esgi.steamstorereactive.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esgi.steamstorereactive.R
import com.esgi.steamstorereactive.model.GameInfo
import com.esgi.steamstorereactive.recyclerview.holder.GameViewHolder
import com.esgi.steamstorereactive.recyclerview.holder.OnGameListener


class GameListAdapter(val items: List<GameInfo>, private val listener: OnGameListener) : RecyclerView.Adapter<GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.game_list_cell, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val item = items[position]
        holder.update(
            items[position]
        )
        holder.itemView.setOnClickListener {
            listener.onClicked(item, position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}