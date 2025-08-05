package com.ryan.sionic.service.validator

import com.ryan.sionic.common.exception.CommonErrorMessage
import com.ryan.sionic.common.exception.HttpCommonException
import com.ryan.sionic.entity.User
import org.springframework.http.HttpStatus

fun validateUserNotExists(user: User?): User {
    return user ?: throw HttpCommonException(HttpStatus.NOT_FOUND, CommonErrorMessage.NOT_EXIST_USER)
}

fun validateUserExists(user: User?) {
    if (user != null) throw HttpCommonException(HttpStatus.CONFLICT, CommonErrorMessage.EXIST_USER)
}

fun validatePassword(isValid: Boolean) {
    if (!isValid) throw HttpCommonException(HttpStatus.UNAUTHORIZED, CommonErrorMessage.PASSWORD_MISS_MATCH)
}