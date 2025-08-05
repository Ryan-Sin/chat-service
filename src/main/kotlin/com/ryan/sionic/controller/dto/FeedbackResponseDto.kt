package com.ryan.sionic.controller.dto

import com.ryan.sionic.common.enums.FeedbackStatus
import io.swagger.v3.oas.annotations.media.Schema
import java.time.Instant

data class FeedbackResponseDto(
    @Schema(description = "피드백 ID", example = "1")
    val id: Long,
    
    @Schema(description = "채팅 ID", example = "1")
    val chatId: Long,
    
    @Schema(description = "질문 내용", example = "AI에 대해 알려주세요.")
    val question: String,
    
    @Schema(description = "답변 내용", example = "AI는 인공지능의 약자로...")
    val answer: String,
    
    @Schema(description = "긍정 피드백 여부", example = "true")
    val isPositive: Boolean,
    
    @Schema(description = "피드백 상태", example = "PENDING")
    val status: FeedbackStatus,
    
    @Schema(description = "생성 시간", example = "2023-01-01T12:00:00")
    val createdAt: Instant
)