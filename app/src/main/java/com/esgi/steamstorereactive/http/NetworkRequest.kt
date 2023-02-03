package com.esgi.steamstorereactive.http

import com.esgi.steamstorereactive.deserializer.GameResponseDeserializer
import com.esgi.steamstorereactive.model.BestSellerResponse
import com.esgi.steamstorereactive.model.GameSearch
import com.esgi.steamstorereactive.model.GameResponse
import com.esgi.steamstorereactive.model.ReviewResponse
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkRequest() {

    private val gameInfoApi = Retrofit.Builder()
        .baseUrl("https://store.steampowered.com/api/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .registerTypeAdapter(
                        GameResponse::class.java,
                        GameResponseDeserializer()
                    )
                    .create()
            )
        )
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(API::class.java)

    private val mostPlayedGameApi = Retrofit.Builder()
        .baseUrl("https://api.steampowered.com/ISteamChartsService/")
        .addConverterFactory(
            GsonConverterFactory.create())
        .addCallAdapterFactory(
            CoroutineCallAdapterFactory())
        .build()
        .create(API::class.java)

    private val searchGameApi = Retrofit.Builder()
        .baseUrl("https://steamcommunity.com/actions/SearchApps/")
        .addConverterFactory(
            GsonConverterFactory.create())
        .addCallAdapterFactory(
            CoroutineCallAdapterFactory())
        .build()
        .create(API::class.java)

    private val getGameReviewsApi = Retrofit.Builder()
        .baseUrl("https://store.steampowered.com/appreviews/")
        .addConverterFactory(
            GsonConverterFactory.create())
        .addCallAdapterFactory(
            CoroutineCallAdapterFactory())
        .build()
        .create(API::class.java)

    suspend fun getGame(id: String): GameResponse {
        return gameInfoApi.getGameById(id).await()
    }

    suspend fun getMostPlayedGame(): BestSellerResponse {
        return mostPlayedGameApi.getMostPlayedGame().await()
    }

    suspend fun searchGame(game: String): List<GameSearch> {
        return searchGameApi.searchGameByName(game).await()
    }

    suspend fun getReviews(id:String): ReviewResponse {
        return getGameReviewsApi.getReviewsByGameId(id, 1).await()
    }
}