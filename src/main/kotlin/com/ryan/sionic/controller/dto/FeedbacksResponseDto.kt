package com.ryan.sionic.controller.dto

import io.swagger.v3.oas.annotations.media.Schema

data class FeedbacksResponseDto(
    @Schema(description = "피드백 목록")
    val feedbacks: List<FeedbackResponseDto>,
    
    @Schema(description = "총 페이지 수", example = "5")
    val totalPages: Int,
    
    @Schema(description = "총 요소 수", example = "100")
    val totalElements: Long,
    
    @Schema(description = "현재 페이지", example = "0")
    val currentPage: Int
)