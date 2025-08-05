package com.ryan.sionic.service.command

data class SignUpCommand(
    val email: String,
    val password: String,
    val name: String
)

data class SignInCommand(
    val email: String,
    val password: String
)