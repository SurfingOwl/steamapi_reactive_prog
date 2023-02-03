package com.esgi.steamstorereactive.gamelibrary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.steamstorereactive.R
import com.esgi.steamstorereactive.http.NetworkRequest
import com.esgi.steamstorereactive.model.GameInfo
import com.esgi.steamstorereactive.recyclerview.adapter.GameListAdapter
import com.esgi.steamstorereactive.recyclerview.holder.OnGameListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class WishlistFragment : Fragment() {

    val requestObject: NetworkRequest = NetworkRequest()
    var wishedGameInfo: List<GameInfo> = listOf()

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

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            try {
                wishedGameInfo = getWishedGame("1")
            } catch (e: IOException) {
                Log.e("WISH_ERR/ ", e.toString())
            }
        }

        gameList.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = GameListAdapter(wishedGameInfo, object : OnGameListener {
                override fun onClicked(game: GameInfo, position: Int) {
                    TODO("Not yet implemented")
                }
            })
        }
        if (wishedGameInfo.isNotEmpty()) {
            gameList.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        }

    }

    private suspend fun getWishedGame(id: String): List<GameInfo> {
        //return withContext(Dispatchers.IO) {
        //}
        return wishedGameInfo
    }
}