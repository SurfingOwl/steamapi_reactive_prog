package com.esgi.steamstorereactive.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CredentialsTransfer(
    val email: String?,
    val password: String?,
) : Parcelable

@Parcelize
data class GameFocusTransfer(
    val gameid: String,
    val userid: String,

) : Parcelable

@Parcelize
data class GameSearchTransfer(
    val gameName: String,
    val userid: String,
) : Parcelable
