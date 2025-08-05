package com.ryan.sionic.persistence

import com.ryan.sionic.entity.Thread
import com.ryan.sionic.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.Optional

@Repository
interface ThreadRepository : JpaRepository<Thread, Long> {
    fun findByUser(user: User, pageable: Pageable): Page<Thread>
    fun findByUserAndCreatedAtAfter(user: User, createdAt: Instant): Optional<Thread>
    fun countByCreatedAtBetween(start: Instant, end: Instant): Long
}