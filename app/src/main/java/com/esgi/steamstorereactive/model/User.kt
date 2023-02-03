package com.esgi.steamstorereactive.model

data class User(
    val id: String?,
    val name: String,
    val credentials: CredentialsTransfer,
)

data class UserResponse(
    val success: Boolean,
    val user: User?,
)