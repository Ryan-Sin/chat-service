package com.ryan.sionic.controller.dto

import io.swagger.v3.oas.annotations.media.Schema

data class SignUpResponseDto(
    @Schema(description = "토큰 정보", example = "eyJhbGciOiJIUzI1NiJ9...")
    val token: String,
    
    @Schema(description = "이메일", example = "example@example.com")
    val email: String,
    
    @Schema(description = "이름", example = "홍길동")
    val name: String,
    
    @Schema(description = "권한", example = "MEMBER")
    val role: String
)