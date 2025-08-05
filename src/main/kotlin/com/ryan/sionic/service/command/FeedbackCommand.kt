package com.ryan.sionic.service.command

import com.ryan.sionic.common.enums.FeedbackStatus

data class CreateFeedbackCommand(
    val email: String,
    val chatId: Long,
    val isPositive: Boolean
)

data class GetFeedbacksCommand(
    val email: String,
    val isPositive: Boolean?,
    val page: Int,
    val size: Int,
    val sort: String
)

data class GetAllFeedbacksCommand(
    val email: String,
    val isPositive: Boolean?,
    val page: Int,
    val size: Int,
    val sort: String
)

data class UpdateFeedbackStatusCommand(
    val email: String,
    val feedbackId: Long,
    val status: FeedbackStatus
)