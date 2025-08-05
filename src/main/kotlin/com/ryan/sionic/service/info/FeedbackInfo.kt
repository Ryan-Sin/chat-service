package com.ryan.sionic.service.info

import com.ryan.sionic.common.enums.FeedbackStatus
import java.time.Instant

data class FeedbackInfo(
    val id: Long,
    val chatId: Long,
    val question: String,
    val answer: String,
    val isPositive: Boolean,
    val status: FeedbackStatus,
    val createdAt: Instant
)

data class FeedbacksInfo(
    val feedbacks: List<FeedbackInfo>,
    val totalPages: Int,
    val totalElements: Long,
    val currentPage: Int
)