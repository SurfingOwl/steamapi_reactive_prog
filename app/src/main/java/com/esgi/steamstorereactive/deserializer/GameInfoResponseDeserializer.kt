package com.esgi.steamstorereactive.deserializer

import com.esgi.steamstorereactive.model.GameInfoResponse
import com.esgi.steamstorereactive.model.GameResponse
import com.google.gson.*
import java.lang.reflect.Type

class GameResponseDeserializer : JsonDeserializer<GameResponse> {

    companion object {
        val deserializer: Gson = GsonBuilder().create()
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GameResponse {
        // On récupère le JSON
        val jsonObject = json?.asJsonObject

        // On récupère la clé du premier élément du json (ex : "578080") qui est un entier
        val key = jsonObject?.keySet()?.first { it.toIntOrNull() != null }

        return GameResponse(
            deserializer.fromJson(
                jsonObject?.get(key), GameInfoResponse::class.java
            )
        )
    }
}