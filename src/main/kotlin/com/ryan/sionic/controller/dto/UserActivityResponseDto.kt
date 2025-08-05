package com.ryan.sionic.controller.dto

import io.swagger.v3.oas.annotations.media.Schema

data class UserActivityResponseDto(
    @Schema(description = "회원가입 수", example = "10")
    val registrations: Long,
    
    @Schema(description = "로그인 수", example = "50")
    val logins: Long,
    
    @Schema(description = "대화 생성 수", example = "100")
    val chats: Long
)