package com.ryan.sionic.controller.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignUpRequestDto(
    @field:NotBlank(message = "이메일은 필수입니다.")
    @field:Email(message = "올바른 이메일 형식이 아닙니다.")
    @Schema(description = "이메일", example = "example@example.com")
    val email: String,
    
    @field:NotBlank(message = "비밀번호는 필수입니다.")
    @field:Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    @Schema(description = "비밀번호", example = "password123")
    val password: String,
    
    @field:NotBlank(message = "이름은 필수입니다.")
    @Schema(description = "이름", example = "홍길동")
    val name: String
)