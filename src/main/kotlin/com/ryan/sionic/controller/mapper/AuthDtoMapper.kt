package com.ryan.sionic.controller.mapper

import com.ryan.sionic.controller.dto.SignInRequestDto
import com.ryan.sionic.controller.dto.SignInResponseDto
import com.ryan.sionic.controller.dto.SignUpRequestDto
import com.ryan.sionic.controller.dto.SignUpResponseDto
import com.ryan.sionic.service.command.SignInCommand
import com.ryan.sionic.service.command.SignUpCommand
import com.ryan.sionic.service.info.TokenInfo
import org.springframework.stereotype.Component

@Component
class AuthDtoMapper {
    fun mapToSignUpCommand(dto: SignUpRequestDto): SignUpCommand {
        return SignUpCommand(
            email = dto.email,
            password = dto.password,
            name = dto.name
        )
    }

    fun mapToSignUpResponse(info: TokenInfo): SignUpResponseDto {
        return SignUpResponseDto(
            token = info.token,
            email = info.email,
            name = info.name,
            role = info.role
        )
    }

    fun mapToSignInCommand(dto: SignInRequestDto): SignInCommand {
        return SignInCommand(
            email = dto.email,
            password = dto.password
        )
    }

    fun mapToSignInResponse(info: TokenInfo): SignInResponseDto {
        return SignInResponseDto(
            token = info.token,
            email = info.email,
            name = info.name,
            role = info.role
        )
    }
}