package com.ryan.sionic.common.exception

object CommonErrorMessage {
    const val INTERNAL_SERVER_ERROR = "서버가 불안정합니다. 잠시 후 다시 시도해주세요."
    const val INVALID_TOKEN = "유효한 토큰이 아닙니다."
    const val NOT_EXIST_UNAUTHORIZED = "유효한 인증 자격 증명이 없습니다."
    
    // 사용자 관련 에러 메시지
    const val EXIST_USER = "이미 등록된 이메일입니다."
    const val NOT_EXIST_USER = "사용자가 존재하지 않습니다."
    const val PASSWORD_MISS_MATCH = "비밀번호가 틀렸습니다."
    
    // 대화 관련 에러 메시지
    const val NOT_EXIST_THREAD = "스레드가 존재하지 않습니다."
    const val NOT_EXIST_CHAT = "대화가 존재하지 않습니다."
    const val UNAUTHORIZED_ACCESS = "접근 권한이 없습니다."
    
    // 피드백 관련 에러 메시지
    const val NOT_EXIST_FEEDBACK = "피드백이 존재하지 않습니다."
    const val EXIST_FEEDBACK = "이미 피드백을 남겼습니다."
}