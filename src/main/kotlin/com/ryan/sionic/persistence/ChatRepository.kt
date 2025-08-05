package com.ryan.sionic.persistence

import com.ryan.sionic.entity.Chat
import com.ryan.sionic.entity.Thread
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface ChatRepository : JpaRepository<Chat, Long> {
    fun findByThread(thread: Thread, pageable: Pageable): Page<Chat>
    
    @Query("SELECT c FROM Chat c JOIN c.thread t JOIN t.user u WHERE u.id = :userId")
    fun findAllByUserId(userId: Long, pageable: Pageable): Page<Chat>
    
    fun countByCreatedAtBetween(start: Instant, end: Instant): Long
    
    fun findByCreatedAtBetween(start: Instant, end: Instant): List<Chat>
}