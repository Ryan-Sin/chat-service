package com.ryan.sionic.service.info

data class UserActivityInfo(
    val registrations: Long,
    val logins: Long,
    val chats: Long
)