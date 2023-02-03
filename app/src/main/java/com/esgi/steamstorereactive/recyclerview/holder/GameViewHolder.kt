package com.esgi.steamstorereactive.recyclerview.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esgi.steamstorereactive.R
import com.esgi.steamstorereactive.model.GameInfo

class GameViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    val background = v.findViewById<ImageView>(R.id.cell_card_bg)
    val thumbnail = v.findViewById<ImageView>(R.id.game_thumbnail)
    val title = v.findViewById<TextView>(R.id.game_title)
    val editor = v.findViewById<TextView>(R.id.editors_name)
    val price = v.findViewById<TextView>(R.id.game_price)

    fun update(game: GameInfo) {
        Glide.with(itemView).load(game.background).into(background)
        Glide.with(itemView).load(game.header_image).into(thumbnail)
        title.text = game.name
        editor.text = game.publishers?.get(0)
        if (!game.is_free) {
            if (!game.price_overview?.final_formatted.isNullOrEmpty())
                price.text = game.price_overview?.final_formatted
            else
                price.text = "NO DATA"
        } else {
            price.text = "F2P"
        }
    }
}