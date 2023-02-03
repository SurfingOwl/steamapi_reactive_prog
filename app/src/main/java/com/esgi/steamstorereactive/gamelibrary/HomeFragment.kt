package com.esgi.steamstorereactive.gamelibrary

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.steamstorereactive.R
import com.esgi.steamstorereactive.http.NetworkRequest
import com.esgi.steamstorereactive.model.GameFocusTransfer
import com.esgi.steamstorereactive.model.GameInfo
import com.esgi.steamstorereactive.model.GameRank
import com.esgi.steamstorereactive.model.GameSearchTransfer
import com.esgi.steamstorereactive.recyclerview.adapter.GameListAdapter
import com.esgi.steamstorereactive.recyclerview.holder.OnGameListener
import kotlinx.coroutines.*
import java.io.IOException

class HomeFragment : Fragment() {

    val requestObject: NetworkRequest = NetworkRequest()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext()).inflate(
            R.layout.home_view, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionbar = (requireActivity() as AppCompatActivity).supportActionBar
        actionbar?.show()
        actionbar?.title = "Home"

        val userid: String = arguments?.get("userid").toString()
        val forwardGameButton = view.findViewById<Button>(R.id.forward_game_button)
        val gameList = view.findViewById<RecyclerView>(R.id.best_seller_list)
        val searchBar = view.findViewById<EditText>(R.id.search_bar)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            try {
                val mostPlayedGames = getMostPlayedGame()
                val trimedGames = mostPlayedGames.subList(0, 10)
                val mostPlayedGameInfo = getGameInfoFromList(trimedGames)

                gameList.apply {
                    layoutManager = LinearLayoutManager(view.context)
                    adapter = GameListAdapter(mostPlayedGameInfo, object : OnGameListener {
                        override fun onClicked(game: GameInfo, position: Int) {
                            findNavController().navigate(
                                HomeFragmentDirections.actionHomeFragmentToGameFocusFragment(
                                    GameFocusTransfer(game.steam_appid, userid)
                                )
                            )
                        }
                    })
                }
            } catch (e: IOException) {
                Log.e("HOME_ERR/ ", e.toString())
            }
        }

        searchBar.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToSearchFragment(
                        GameSearchTransfer(searchBar.text.toString(), userid)
                    )
                )
                return@OnKeyListener true
            }
            false
        })

        forwardGameButton.setOnClickListener { _ ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToGameFocusFragment(GameFocusTransfer("1237970", userid))
            )
        }
    }

    private suspend fun getMostPlayedGame(): List<GameRank> {
        return withContext(Dispatchers.IO) {
            requestObject.getMostPlayedGame().response.ranks
        }
    }

    private suspend fun getGameInfo(id: String): GameInfo {
        return withContext(Dispatchers.IO) {
            requestObject.getGame(id).game.data
        }
    }

    private suspend fun getGameInfoFromList(list: List<GameRank>): List<GameInfo> {
        var newList = listOf<GameInfo>()
        list.forEach { game ->
            newList = newList.plus(withContext(Dispatchers.IO) {
                requestObject.getGame(game.appid).game.data
            })
        }
        return newList
    }
}