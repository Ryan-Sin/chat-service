package com.ryan.sionic.service

import com.ryan.sionic.entity.User
import com.ryan.sionic.persistence.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun save(user: User): User {
        return userRepository.save(user)
    }
}