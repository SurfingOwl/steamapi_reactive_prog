package com.esgi.steamstorereactive.gamelibrary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.steamstorereactive.R
import com.esgi.steamstorereactive.http.NetworkRequest
import com.esgi.steamstorereactive.model.GameIds
import com.esgi.steamstorereactive.model.GameInfo
import com.esgi.steamstorereactive.model.GameInfoResponse
import com.esgi.steamstorereactive.model.GameSearch
import com.esgi.steamstorereactive.recyclerview.adapter.GameListAdapter
import com.esgi.steamstorereactive.recyclerview.OnGameListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class WishlistFragment : Fragment() {

    val requestObject: NetworkRequest = NetworkRequest()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext()).inflate(
            R.layout.wishlist_view, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionbar = (requireActivity() as AppCompatActivity).supportActionBar
        actionbar?.show()
        actionbar?.title = "Wishlist"

        val gameList = view.findViewById<RecyclerView>(R.id.wishlist)
        val emptyView = view.findViewById<ConstraintLayout>(R.id.empty_wishlist)

        val loader = view.findViewById<ProgressBar>(R.id.pgbar)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            loader.visibility = View.VISIBLE
            try {
                val wishedGameIds = getWishedGame(listOf("1"))

                if (!wishedGameIds.ids.isNullOrEmpty()) {
                    gameList.visibility = View.VISIBLE
                    emptyView.visibility = View.GONE
                }

                val wishedGameInfos = getSearchedGameInfos(wishedGameIds.ids, loader)
                gameList.apply {
                    layoutManager = LinearLayoutManager(view.context)
                    adapter = GameListAdapter(wishedGameInfos, object : OnGameListener {
                        override fun onClicked(game: GameInfo, position: Int) {
                            TODO("Not yet implemented")
                        }
                    })
                }
            } catch (e: IOException) {
                Log.e("WISH_ERR/ ", e.toString())
            }
        }
    }

    private suspend fun getWishedGame(id: List<String>): GameIds {
        //return withContext(Dispatchers.IO) {
        //    requestObject.getWishedGames(userid)
        //}
        return GameIds(listOf("1", "2", "3"))
    }

    private suspend fun getSearchedGameInfos(list: List<String>?, loader: ProgressBar) : List<GameInfo> {
        var newList = listOf<GameInfoResponse>()
        var finalList = listOf<GameInfo>()
        list?.forEach { id ->
            newList = newList.plus(withContext(Dispatchers.IO) {
                requestObject.getGame(id).game
            })
        }
        newList.filter { gameInfoResponse -> gameInfoResponse.success }
        newList.forEach { gameInfoResponse ->
            finalList = finalList.plus(gameInfoResponse.data)
        }
        loader.visibility = View.GONE
        return finalList
    }

}