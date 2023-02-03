package com.esgi.steamstorereactive.model

data class BestSellerResponse(
    val response: GameRanks,
)

data class GameRank(
    val rank: String,
    val appid: String,
)

data class GameRanks(
    val ranks: List<GameRank>,
)

data class GameSearch(
    val appid: String,
    val name: String,
    val icon: String,
    val logo: String,
)

data class GameResponse(
    val game: GameInfoResponse,
)

data class GameInfoResponse(
    val success: Boolean,
    val data: GameInfo,
)

data class GameInfo(
    val name: String,
    val steam_appid: String,
    val is_free: Boolean,
    val detailed_description: String?,
    val short_description: String?,
    val header_image: String?,
    val publishers: List<String>?,
    val price_overview: Price?,
    val background: String?,
)

data class Price(
    val final_formatted: String?,
)

data class ReviewResponse(
    val success: Int,
    val reviews: List<Review>,
)

data class Review(
    val recommendationid: String,
    val author: Author,
    val language: String,
    val review: String,
    val voted_up: Boolean,
)

data class Author(
    val steamid: String,
)
