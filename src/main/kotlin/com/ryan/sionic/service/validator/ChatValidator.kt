package com.ryan.sionic.service.validator

import com.ryan.sionic.common.enums.UserRole
import com.ryan.sionic.common.exception.CommonErrorMessage
import com.ryan.sionic.common.exception.HttpCommonException
import com.ryan.sionic.entity.Chat
import com.ryan.sionic.entity.Thread
import com.ryan.sionic.entity.User
import org.springframework.http.HttpStatus

fun validateThreadNotExists(thread: Thread?): Thread {
    return thread ?: throw HttpCommonException(HttpStatus.NOT_FOUND, CommonErrorMessage.NOT_EXIST_THREAD)
}

fun validateChatNotExists(chat: Chat?): Chat {
    return chat ?: throw HttpCommonException(HttpStatus.NOT_FOUND, CommonErrorMessage.NOT_EXIST_CHAT)
}

fun validateThreadOwnership(thread: Thread, user: User) {
    if (thread.user.id != user.id && user.role != UserRole.ADMIN) {
        throw HttpCommonException(HttpStatus.FORBIDDEN, CommonErrorMessage.UNAUTHORIZED_ACCESS)
    }
}

fun validateAdminRole(user: User) {
    if (user.role != UserRole.ADMIN) {
        throw HttpCommonException(HttpStatus.FORBIDDEN, CommonErrorMessage.UNAUTHORIZED_ACCESS)
    }
}