package com.ryan.sionic.controller.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

data class FeedbackRequestDto(
    @field:NotNull(message = "채팅 ID는 필수입니다.")
    @Schema(description = "채팅 ID", example = "1")
    val chatId: Long,
    
    @field:NotNull(message = "피드백 유형은 필수입니다.")
    @Schema(description = "긍정 피드백 여부", example = "true")
    val isPositive: Boolean
)