package com.esgi.steamstorereactive.http

import com.esgi.steamstorereactive.model.*
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("GetMostPlayedGames/v1")
    fun getMostPlayedGame(): Deferred<BestSellerResponse>

    @GET("{game}")
    fun searchGameByName(@Path("game") name: String): Deferred<List<GameSearch>>

    @GET("appdetails")
    fun getGameById(@Query("appids") id: String): Deferred<GameResponse>

    @GET("{id}")
    fun getReviewsByGameId(@Path("id") id: String, @Query("json") json: Int): Deferred<ReviewResponse>

//    @GET("{id}")
//    fun getUsernameById(@Path("id") id: Int, @Query("xml") value: Int): Deferred<Author> // TODO DATA CLASS FOR USER

}