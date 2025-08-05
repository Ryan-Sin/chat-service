package com.ryan.sionic.controller.dto

import com.ryan.sionic.common.enums.FeedbackStatus
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

data class UpdateFeedbackStatusRequestDto(
    @field:NotNull(message = "상태는 필수입니다.")
    @Schema(description = "피드백 상태", example = "RESOLVED")
    val status: FeedbackStatus
)