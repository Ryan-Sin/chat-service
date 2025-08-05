package com.ryan.sionic.persistence

import com.ryan.sionic.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun countByCreatedAtBetween(start: Instant, end: Instant): Long
}