package com.ryan.sionic.persistence

import com.ryan.sionic.entity.Chat
import com.ryan.sionic.entity.Feedback
import com.ryan.sionic.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface FeedbackRepository : JpaRepository<Feedback, Long> {
    fun findByUser(user: User, pageable: Pageable): Page<Feedback>
    fun findByUserAndIsPositive(user: User, isPositive: Boolean, pageable: Pageable): Page<Feedback>
    fun findByUserAndChat(user: User, chat: Chat): Optional<Feedback>
    fun existsByUserAndChat(user: User, chat: Chat): Boolean
    fun findByIsPositive(isPositive: Boolean, pageable: Pageable): Page<Feedback>
}