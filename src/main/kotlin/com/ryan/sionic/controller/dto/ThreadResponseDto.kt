package com.ryan.sionic.controller.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.Instant

data class ThreadResponseDto(
    @Schema(description = "스레드 ID", example = "1")
    val id: Long,
    
    @Schema(description = "생성 시간", example = "2023-01-01T12:00:00")
    val createdAt: Instant,
    
    @Schema(description = "채팅 목록")
    val chats: List<ChatResponseDto>
)