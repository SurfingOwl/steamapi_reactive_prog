package com.esgi.steamstorereactive.gamelibrary

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.steamstorereactive.R
import com.esgi.steamstorereactive.http.NetworkRequest
import com.esgi.steamstorereactive.model.*
import com.esgi.steamstorereactive.recyclerview.adapter.GameListAdapter
import com.esgi.steamstorereactive.recyclerview.holder.OnGameListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class SearchFragment : Fragment() {

    val requestObject: NetworkRequest = NetworkRequest()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext()).inflate(
            R.layout.search_view, container, false
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: GameSearchTransfer = arguments?.get("gameSearchTransfer") as GameSearchTransfer
        val userid: String = args.userid
        val gameName = args.gameName

        val searchBar = view.findViewById<EditText>(R.id.search_bar)
        val searchResult: TextView = view.findViewById<EditText>(R.id.search_result_text)
        val gameList = view.findViewById<RecyclerView>(R.id.game_search_result)
        val loader = view.findViewById<ProgressBar>(R.id.pgbar)

        searchBar.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                onViewCreated(view, savedInstanceState)
                return@OnKeyListener true
            }
            false
        })

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            loader.visibility = View.VISIBLE
            try {
                val mostPlayedGames = getSearchedGame(gameName)
                val searchedGameInfo = getSearchedGameInfos(mostPlayedGames, loader)
                gameList.apply {
                    layoutManager = LinearLayoutManager(view.context)
                    adapter = GameListAdapter(searchedGameInfo, object : OnGameListener {
                        override fun onClicked(game: GameInfo, position: Int) {
                            findNavController().navigate(
                                SearchFragmentDirections.actionSearchFragmentToGameFocusFragment(
                                    GameFocusTransfer(game.steam_appid, userid)
                                )
                            )
                        }
                    })
                }
                searchResult.text = searchResult.text.subSequence(0, searchResult.text.lastIndexOf(":")).toString() + setResultNumber(searchedGameInfo).toString()

            } catch (e: IOException) {
                Log.e("SEARCH_ERR/ ", e.toString())
            }
        }
    }

    private suspend fun getSearchedGame(game: String): List<GameSearch> {
        return withContext(Dispatchers.IO) {
            requestObject.searchGame(game)
        }
    }

    private suspend fun getGameInfo(id: String): GameInfo {
        return withContext(Dispatchers.IO) {
            requestObject.getGame(id).game.data
        }
    }

    private suspend fun getSearchedGameInfos(list: List<GameSearch>, loader: ProgressBar) : List<GameInfo> {
        var newList = listOf<GameInfoResponse>()
        var finalList = listOf<GameInfo>()
        list.forEach { game ->
            newList = newList.plus(withContext(Dispatchers.IO) {
                requestObject.getGame(game.appid).game
            })
        }
        newList.filter { gameInfoResponse -> gameInfoResponse.success }
        newList.forEach { gameInfoResponse ->
            finalList = finalList.plus(gameInfoResponse.data)
        }
        loader.visibility = View.GONE
        return finalList
    }

    private fun setResultNumber(games: List<GameInfo>): Int {
        return games.size
    }
}