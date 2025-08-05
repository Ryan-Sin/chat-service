package com.ryan.sionic.controller

import com.ryan.sionic.common.dto.SuccessResponseDto
import com.ryan.sionic.controller.dto.SignInRequestDto
import com.ryan.sionic.controller.dto.SignInResponseDto
import com.ryan.sionic.controller.dto.SignUpRequestDto
import com.ryan.sionic.controller.dto.SignUpResponseDto
import com.ryan.sionic.controller.mapper.AuthDtoMapper
import com.ryan.sionic.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authDtoMapper: AuthDtoMapper,
    private val authService: AuthService
) {

    @PostMapping("/sign-up")
    @Operation(
        summary = "회원가입", description = "이메일, 비밀번호, 이름을 입력받아 회원가입을 진행합니다.",
        responses = [
            ApiResponse(
                responseCode = "200", description = "응답 정보",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = SignUpResponseDto::class)
                    )
                ]
            ),
        ]
    )
    fun onSignUp(@RequestBody @Valid dto: SignUpRequestDto): SuccessResponseDto<SignUpResponseDto> {
        val command = authDtoMapper.mapToSignUpCommand(dto)
        val data = authService.onSignUp(command)
        val result = authDtoMapper.mapToSignUpResponse(data)
        return SuccessResponseDto(data = result)
    }

    @PostMapping("/sign-in")
    @Operation(
        summary = "로그인", description = "이메일, 비밀번호를 입력받아 로그인을 진행합니다.",
        responses = [
            ApiResponse(
                responseCode = "200", description = "응답 정보",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = SignInResponseDto::class)
                    )
                ]
            ),
        ]
    )
    fun onSignIn(@RequestBody @Valid dto: SignInRequestDto): SuccessResponseDto<SignInResponseDto> {
        val command = authDtoMapper.mapToSignInCommand(dto)
        val data = authService.onSignIn(command)
        val result = authDtoMapper.mapToSignInResponse(data)
        return SuccessResponseDto(data = result)
    }
}