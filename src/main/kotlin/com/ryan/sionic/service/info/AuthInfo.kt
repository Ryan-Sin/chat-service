package com.ryan.sionic.service.info

data class TokenInfo(
    val token: String,
    val email: String,
    val name: String,
    val role: String
)