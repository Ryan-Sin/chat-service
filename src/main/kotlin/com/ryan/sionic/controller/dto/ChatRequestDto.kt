package com.ryan.sionic.controller.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class ChatRequestDto(
    @field:NotBlank(message = "질문은 필수입니다.")
    @Schema(description = "질문 내용", example = "AI에 대해 알려주세요.")
    val question: String,
    
    @Schema(description = "스트리밍 응답 여부", example = "false")
    val isStreaming: Boolean = false,
    
    @Schema(description = "사용할 모델", example = "default", nullable = true)
    val model: String? = null
)