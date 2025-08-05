package com.ryan.sionic.service.info

import java.time.Instant

data class ChatInfo(
    val id: Long,
    val question: String,
    val answer: String,
    val createdAt: Instant
)

data class ThreadInfo(
    val id: Long,
    val createdAt: Instant,
    val chats: List<ChatInfo>
)

data class ThreadsInfo(
    val threads: List<ThreadInfo>,
    val totalPages: Int,
    val totalElements: Long,
    val currentPage: Int
)