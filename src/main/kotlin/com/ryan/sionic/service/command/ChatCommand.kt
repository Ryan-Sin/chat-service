package com.ryan.sionic.service.command

data class CreateChatCommand(
    val email: String,
    val question: String,
    val isStreaming: Boolean = false,
    val model: String? = null
)

data class GetChatsCommand(
    val email: String,
    val page: Int,
    val size: Int,
    val sort: String
)

data class GetAllChatsCommand(
    val email: String,
    val page: Int,
    val size: Int,
    val sort: String
)

data class DeleteThreadCommand(
    val email: String,
    val threadId: Long
)