package com.ryan.sionic.service

import com.ryan.sionic.common.exception.CommonErrorMessage
import com.ryan.sionic.common.exception.HttpCommonException
import com.ryan.sionic.config.jwt.JwtTokenProvider
import com.ryan.sionic.entity.User
import com.ryan.sionic.service.command.SignInCommand
import com.ryan.sionic.service.command.SignUpCommand
import com.ryan.sionic.service.info.TokenInfo
import com.ryan.sionic.service.validator.validateUserExists
import com.ryan.sionic.service.validator.validateUserNotExists
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
) {

    fun onSignUp(command: SignUpCommand): TokenInfo {
        validateUserExists(userService.findByEmail(command.email))

        val user = User(
            email = command.email,
            password = passwordEncoder.encode(command.password),
            name = command.name
        )

        val savedUser = userService.save(user)
        val token = jwtTokenProvider.createToken(savedUser.email, savedUser.role)

        return TokenInfo(
            token = token,
            email = savedUser.email,
            name = savedUser.name,
            role = savedUser.role.name
        )
    }

    fun onSignIn(command: SignInCommand): TokenInfo {
        val user = validateUserNotExists(this.userService.findByEmail(command.email))
        this.validatePasswordMissMatches(command.password, user.password)
        val token = jwtTokenProvider.createToken(user.email, user.role)

        return TokenInfo(
            token = token,
            email = user.email,
            name = user.name,
            role = user.role.name
        )
    }

    private fun validatePasswordMissMatches(newPassword: String, oldPassword: String) {
        if(!this.passwordEncoder.matches(newPassword, oldPassword))
            throw HttpCommonException(HttpStatus.BAD_REQUEST, CommonErrorMessage.PASSWORD_MISS_MATCH)
    }
}