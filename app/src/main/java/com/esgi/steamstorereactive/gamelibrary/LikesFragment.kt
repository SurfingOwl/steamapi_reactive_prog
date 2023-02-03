package com.esgi.steamstorereactive.gamelibrary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.steamstorereactive.R
import com.esgi.steamstorereactive.http.NetworkRequest
import com.esgi.steamstorereactive.model.GameFocusTransfer
import com.esgi.steamstorereactive.model.GameInfo
import com.esgi.steamstorereactive.recyclerview.adapter.GameListAdapter
import com.esgi.steamstorereactive.recyclerview.holder.OnGameListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class LikesFragment : Fragment() {

    val requestObject: NetworkRequest = NetworkRequest()
    var likedGameInfo: List<GameInfo> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext()).inflate(
            R.layout.likes_view, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userid = arguments.toString()

        val gameList = view.findViewById<RecyclerView>(R.id.likes)
        val emptyView = view.findViewById<ConstraintLayout>(R.id.empty_likes)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            try {
                likedGameInfo = getLikedGame(userid)
            } catch (e: IOException) {
                Log.e("LIKES_ERR/ ", e.toString())
            }
        }

        gameList.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = GameListAdapter(likedGameInfo, object : OnGameListener {
                override fun onClicked(game: GameInfo, position: Int) {
                    findNavController().navigate(
                        LikesFragmentDirections.actionLikesFragmentToGameFocusFragment(
                            GameFocusTransfer(game.steam_appid, userid)
                        )
                    )

                }
            })
        }
        if (likedGameInfo.isNotEmpty()) {
            gameList.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        }
    }

    private suspend fun getLikedGame(id: String): List<GameInfo> {
        //return withContext(Dispatchers.IO) {
        //}
        return likedGameInfo
    }
}