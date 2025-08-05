package com.ryan.sionic.service

import com.ryan.sionic.entity.Feedback
import com.ryan.sionic.persistence.ChatRepository
import com.ryan.sionic.persistence.FeedbackRepository
import com.ryan.sionic.service.command.CreateFeedbackCommand
import com.ryan.sionic.service.command.GetAllFeedbacksCommand
import com.ryan.sionic.service.command.GetFeedbacksCommand
import com.ryan.sionic.service.command.UpdateFeedbackStatusCommand
import com.ryan.sionic.service.info.FeedbackInfo
import com.ryan.sionic.service.info.FeedbacksInfo
import com.ryan.sionic.service.validator.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class FeedbackService(
    private val userService: UserService,
    private val feedbackRepository: FeedbackRepository,
    private val chatRepository: ChatRepository
) {

    fun createFeedback(command: CreateFeedbackCommand): FeedbackInfo {
        val user = validateUserNotExists(userService.findByEmail(command.email))
        val validChat = validateChatNotExists(chatRepository.findById(command.chatId).orElse(null))
        validateChatOwnership(validChat, user)
        validateFeedbackNotExistsForChat(feedbackRepository.existsByUserAndChat(user, validChat))
        
        val feedback = Feedback(
            user = user,
            chat = validChat,
            isPositive = command.isPositive
        )
        
        val savedFeedback = feedbackRepository.save(feedback)
        
        return FeedbackInfo(
            id = savedFeedback.id!!,
            chatId = validChat.id!!,
            question = validChat.question,
            answer = validChat.answer,
            isPositive = savedFeedback.isPositive,
            status = savedFeedback.status,
            createdAt = savedFeedback.createdAt
        )
    }

    fun getFeedbacks(command: GetFeedbacksCommand): FeedbacksInfo {
        val user = validateUserNotExists(userService.findByEmail(command.email))
        
        val direction = if (command.sort.equals("ASC", ignoreCase = true)) 
            Sort.Direction.ASC else Sort.Direction.DESC
        val pageable = PageRequest.of(command.page, command.size, Sort.by(direction, "createdAt"))
        
        val feedbackPage = if (command.isPositive != null) {
            feedbackRepository.findByUserAndIsPositive(user, command.isPositive, pageable)
        } else {
            feedbackRepository.findByUser(user, pageable)
        }
        
        return FeedbacksInfo(
            feedbacks = feedbackPage.content.map { feedback ->
                FeedbackInfo(
                    id = feedback.id!!,
                    chatId = feedback.chat.id!!,
                    question = feedback.chat.question,
                    answer = feedback.chat.answer,
                    isPositive = feedback.isPositive,
                    status = feedback.status,
                    createdAt = feedback.createdAt
                )
            },
            totalPages = feedbackPage.totalPages,
            totalElements = feedbackPage.totalElements,
            currentPage = command.page
        )
    }

    fun getAllFeedbacks(command: GetAllFeedbacksCommand): FeedbacksInfo {
        val user = validateUserNotExists(userService.findByEmail(command.email))
        validateAdminRole(user)
        
        val direction = if (command.sort.equals("ASC", ignoreCase = true)) 
            Sort.Direction.ASC else Sort.Direction.DESC
        val pageable = PageRequest.of(command.page, command.size, Sort.by(direction, "createdAt"))
        
        val feedbackPage = if (command.isPositive != null) {
            feedbackRepository.findByIsPositive(command.isPositive, pageable)
        } else {
            feedbackRepository.findAll(pageable)
        }
        
        return FeedbacksInfo(
            feedbacks = feedbackPage.content.map { feedback ->
                FeedbackInfo(
                    id = feedback.id!!,
                    chatId = feedback.chat.id!!,
                    question = feedback.chat.question,
                    answer = feedback.chat.answer,
                    isPositive = feedback.isPositive,
                    status = feedback.status,
                    createdAt = feedback.createdAt
                )
            },
            totalPages = feedbackPage.totalPages,
            totalElements = feedbackPage.totalElements,
            currentPage = command.page
        )
    }

    fun updateFeedbackStatus(command: UpdateFeedbackStatusCommand): FeedbackInfo {
        val user = validateUserNotExists(userService.findByEmail(command.email))
        validateAdminRole(user)
        
        val feedback = feedbackRepository.findById(command.feedbackId).orElse(null)
        val validFeedback = validateFeedbackNotExists(feedback)
        
        validFeedback.status = command.status
        val updatedFeedback = feedbackRepository.save(validFeedback)
        
        return FeedbackInfo(
            id = updatedFeedback.id!!,
            chatId = updatedFeedback.chat.id!!,
            question = updatedFeedback.chat.question,
            answer = updatedFeedback.chat.answer,
            isPositive = updatedFeedback.isPositive,
            status = updatedFeedback.status,
            createdAt = updatedFeedback.createdAt
        )
    }
}