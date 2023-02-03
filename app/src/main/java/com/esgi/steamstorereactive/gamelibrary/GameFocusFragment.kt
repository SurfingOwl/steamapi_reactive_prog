package com.esgi.steamstorereactive.gamelibrary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esgi.steamstorereactive.R
import com.esgi.steamstorereactive.http.NetworkRequest
import com.esgi.steamstorereactive.model.GameFocusTransfer
import com.esgi.steamstorereactive.model.GameInfo
import com.esgi.steamstorereactive.model.ReviewResponse
import com.esgi.steamstorereactive.recyclerview.adapter.ReviewListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class GameFocusFragment : Fragment() {

    val requestObject: NetworkRequest = NetworkRequest()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext()).inflate(
            R.layout.game_focus_view, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionbar = (requireActivity() as AppCompatActivity).supportActionBar
        actionbar?.show()
        actionbar?.title = "Détails du jeu"
        actionbar?.setDisplayHomeAsUpEnabled(true)

        val ids: GameFocusTransfer = arguments?.get("gameFocusIds") as GameFocusTransfer

        val tabDescription = view.findViewById<Button>(R.id.description_tab)
        val tabReview = view.findViewById<Button>(R.id.reviews_tab)
        val gameReviews = view.findViewById<RecyclerView>(R.id.game_reviews)
        val gameDescription = view.findViewById<TextView>(R.id.game_description)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            try {
                val gameInfo = getGameInfo(ids.gameid)
                val reviewList = getReviews(ids.gameid)

                setViewElement(view, gameDescription, gameInfo)

                gameReviews.apply {
                    layoutManager = LinearLayoutManager(view.context)
                    adapter = ReviewListAdapter(reviewList.reviews)
                }
            } catch (e: IOException) {
                Log.e("FOCUS_ERR/ ", e.toString())
            }
        }

        tabDescription.setOnClickListener { _ ->
            tabDescription.setBackgroundResource(R.drawable.left_selected_tab)
            tabReview.setBackgroundResource(R.drawable.right_unselected_tab)
            gameDescription.visibility = View.VISIBLE
            gameReviews.visibility = View.GONE
        }
        tabReview.setOnClickListener { _ ->
            tabDescription.setBackgroundResource(R.drawable.left_unselected_tab)
            tabReview.setBackgroundResource(R.drawable.right_selected_tab)
            gameDescription.visibility = View.GONE
            gameReviews.visibility = View.VISIBLE
        }
    }

    private fun setViewElement(view: View, gameDescription: TextView, gameInfo: GameInfo) {
        val gameDisplayImage = view.findViewById<ImageView>(R.id.game_display_image)
        val gameCardBg = view.findViewById<ImageView>(R.id.game_card_bg)
        val gameThumbnail = view.findViewById<ImageView>(R.id.game_thumbnail)
        val gameCardTitle = view.findViewById<TextView>(R.id.game_card_title)
        val publisherName = view.findViewById<TextView>(R.id.publisher_name)

        Glide.with(view).load(gameInfo.background).into(gameDisplayImage)
        Glide.with(view).load(gameInfo.background).into(gameCardBg)
        Glide.with(view).load(gameInfo.header_image).into(gameThumbnail)
        gameCardTitle.text = gameInfo.name
        publisherName.text = gameInfo.publishers?.get(0)
        gameDescription.text = gameInfo.short_description
    }


    private suspend fun getReviews(id: String): ReviewResponse {
        return withContext(Dispatchers.IO) {
            requestObject.getReviews(id)
        }
    }

    private suspend fun getGameInfo(id: String): GameInfo {
        return withContext(Dispatchers.IO) {
            requestObject.getGame(id).game.data
        }
    }

    private suspend fun addGameToWishlist(game: GameInfo, user: String): Boolean {return true}
    private suspend fun removeGameFromWishList(game: GameInfo, user: String): Boolean {return true}
    private suspend fun addGameToLikes(game: GameInfo, user: String): Boolean {return true}
    private suspend fun removeGameFromLikes(game: GameInfo, user: String): Boolean {return true}

}