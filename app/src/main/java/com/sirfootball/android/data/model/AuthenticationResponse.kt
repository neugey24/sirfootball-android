package com.sirfootball.android.data.model

data class AuthenticationResponse (
    val result: String,
    val detail: String,
    val token: String,
    val userId: Int,
    val username: String,
    val crownCount: Int
)