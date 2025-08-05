package com.ryan.sionic.service.validator

import com.ryan.sionic.common.enums.UserRole
import com.ryan.sionic.common.exception.CommonErrorMessage
import com.ryan.sionic.common.exception.HttpCommonException
import com.ryan.sionic.entity.Chat
import com.ryan.sionic.entity.Feedback
import com.ryan.sionic.entity.User
import org.springframework.http.HttpStatus

fun validateFeedbackNotExists(feedback: Feedback?): Feedback {
    return feedback ?: throw HttpCommonException(HttpStatus.NOT_FOUND, CommonErrorMessage.NOT_EXIST_FEEDBACK)
}

fun validateFeedbackNotExistsForChat(exists: Boolean) {
    if (exists) throw HttpCommonException(HttpStatus.CONFLICT, CommonErrorMessage.EXIST_FEEDBACK)
}

fun validateChatOwnership(chat: Chat, user: User) {
    if (chat.thread.user.id != user.id && user.role != UserRole.ADMIN) {
        throw HttpCommonException(HttpStatus.FORBIDDEN, CommonErrorMessage.UNAUTHORIZED_ACCESS)
    }
}