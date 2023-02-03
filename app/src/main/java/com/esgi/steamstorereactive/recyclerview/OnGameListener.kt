package com.esgi.steamstorereactive.recyclerview

import com.esgi.steamstorereactive.model.GameInfo

interface OnGameListener {
    fun onClicked(game: GameInfo, position: Int)
}