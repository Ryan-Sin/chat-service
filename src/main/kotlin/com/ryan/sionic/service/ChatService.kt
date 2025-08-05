package com.ryan.sionic.service

import com.ryan.sionic.entity.Chat
import com.ryan.sionic.entity.Thread
import com.ryan.sionic.entity.User
import com.ryan.sionic.persistence.ChatRepository
import com.ryan.sionic.persistence.ThreadRepository
import com.ryan.sionic.service.command.CreateChatCommand
import com.ryan.sionic.service.command.DeleteThreadCommand
import com.ryan.sionic.service.command.GetAllChatsCommand
import com.ryan.sionic.service.command.GetChatsCommand
import com.ryan.sionic.service.info.ChatInfo
import com.ryan.sionic.service.info.ThreadInfo
import com.ryan.sionic.service.info.ThreadsInfo
import com.ryan.sionic.service.validator.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class ChatService(
    private val userService: UserService,
    private val threadRepository: ThreadRepository,
    private val chatRepository: ChatRepository,
    @Value("\${thread.timeout:1800}") private val threadTimeoutSeconds: Long
) {

    fun createChat(command: CreateChatCommand): ChatInfo {
        val user = validateUserNotExists(userService.findByEmail(command.email))

        val thread = getOrCreateThread(user)
        val response = "질문에 대한 답변입니다: ${command.question}"
        
        val chat = Chat(
            question = command.question,
            answer = response,
            thread = thread
        )
        
        val savedChat = chatRepository.save(chat)
        
        return ChatInfo(
            id = savedChat.id!!,
            question = savedChat.question,
            answer = savedChat.answer,
            createdAt = savedChat.createdAt
        )
    }

    fun getChats(command: GetChatsCommand): ThreadsInfo {
        val user = validateUserNotExists(userService.findByEmail(command.email))

        val direction = if (command.sort.equals("ASC", ignoreCase = true)) 
            Sort.Direction.ASC else Sort.Direction.DESC
        val pageable = PageRequest.of(command.page, command.size, Sort.by(direction, "createdAt"))
        
        val threads = threadRepository.findByUser(user, pageable)
        
        return ThreadsInfo(
            threads = threads.content.map { thread ->
                val chats = chatRepository.findByThread(thread, PageRequest.of(0, 100))
                ThreadInfo(
                    id = thread.id!!,
                    createdAt = thread.createdAt,
                    chats = chats.content.map { chat ->
                        ChatInfo(
                            id = chat.id!!,
                            question = chat.question,
                            answer = chat.answer,
                            createdAt = chat.createdAt
                        )
                    }
                )
            },
            totalPages = threads.totalPages,
            totalElements = threads.totalElements,
            currentPage = command.page
        )
    }

    fun getAllChats(command: GetAllChatsCommand): ThreadsInfo {
        val user = validateUserNotExists(userService.findByEmail(command.email))
        validateAdminRole(user)
        
        val direction = if (command.sort.equals("ASC", ignoreCase = true)) 
            Sort.Direction.ASC else Sort.Direction.DESC
        val pageable = PageRequest.of(command.page, command.size, Sort.by(direction, "createdAt"))
        
        val threads = threadRepository.findAll(pageable)
        
        return ThreadsInfo(
            threads = threads.content.map { thread ->
                val chats = chatRepository.findByThread(thread, PageRequest.of(0, 100))
                ThreadInfo(
                    id = thread.id!!,
                    createdAt = thread.createdAt,
                    chats = chats.content.map { chat ->
                        ChatInfo(
                            id = chat.id!!,
                            question = chat.question,
                            answer = chat.answer,
                            createdAt = chat.createdAt
                        )
                    }
                )
            },
            totalPages = threads.totalPages,
            totalElements = threads.totalElements,
            currentPage = command.page
        )
    }

    @Transactional
    fun deleteThread(command: DeleteThreadCommand) {
        val user = validateUserNotExists(userService.findByEmail(command.email))
        val thread = threadRepository.findById(command.threadId).orElse(null)
        val validThread = validateThreadNotExists(thread)
        validateThreadOwnership(validThread, user)
        
        threadRepository.delete(validThread)
    }

    private fun getOrCreateThread(user: User): Thread {
        // 마지막 질문으로부터 30분 이내인 스레드 찾기 (Instant 직접 사용)
        val threadCutoff = Instant.now().minus(threadTimeoutSeconds, ChronoUnit.SECONDS)
        
        return threadRepository.findByUserAndCreatedAtAfter(user, threadCutoff)
            .orElseGet { threadRepository.save(Thread(user = user)) }
    }
}