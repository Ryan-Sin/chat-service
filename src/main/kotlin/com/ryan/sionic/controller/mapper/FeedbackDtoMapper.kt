package com.ryan.sionic.controller.mapper

import com.ryan.sionic.controller.dto.FeedbackRequestDto
import com.ryan.sionic.controller.dto.FeedbackResponseDto
import com.ryan.sionic.controller.dto.FeedbacksResponseDto
import com.ryan.sionic.controller.dto.UpdateFeedbackStatusRequestDto
import com.ryan.sionic.service.command.CreateFeedbackCommand
import com.ryan.sionic.service.command.GetAllFeedbacksCommand
import com.ryan.sionic.service.command.GetFeedbacksCommand
import com.ryan.sionic.service.command.UpdateFeedbackStatusCommand
import com.ryan.sionic.service.info.FeedbackInfo
import com.ryan.sionic.service.info.FeedbacksInfo
import org.springframework.stereotype.Component

@Component
class FeedbackDtoMapper {
    fun mapToCreateFeedbackCommand(email: String, dto: FeedbackRequestDto): CreateFeedbackCommand {
        return CreateFeedbackCommand(
            email = email,
            chatId = dto.chatId,
            isPositive = dto.isPositive
        )
    }

    fun mapToGetFeedbacksCommand(email: String, isPositive: Boolean?, page: Int, size: Int, sort: String): GetFeedbacksCommand {
        return GetFeedbacksCommand(
            email = email,
            isPositive = isPositive,
            page = page,
            size = size,
            sort = sort
        )
    }

    fun mapToGetAllFeedbacksCommand(email: String, isPositive: Boolean?, page: Int, size: Int, sort: String): GetAllFeedbacksCommand {
        return GetAllFeedbacksCommand(
            email = email,
            isPositive = isPositive,
            page = page,
            size = size,
            sort = sort
        )
    }

    fun mapToUpdateFeedbackStatusCommand(email: String, feedbackId: Long, dto: UpdateFeedbackStatusRequestDto): UpdateFeedbackStatusCommand {
        return UpdateFeedbackStatusCommand(
            email = email,
            feedbackId = feedbackId,
            status = dto.status
        )
    }

    fun mapToFeedbackResponse(info: FeedbackInfo): FeedbackResponseDto {
        return FeedbackResponseDto(
            id = info.id,
            chatId = info.chatId,
            question = info.question,
            answer = info.answer,
            isPositive = info.isPositive,
            status = info.status,
            createdAt = info.createdAt
        )
    }

    fun mapToFeedbacksResponse(info: FeedbacksInfo): FeedbacksResponseDto {
        return FeedbacksResponseDto(
            feedbacks = info.feedbacks.map { mapToFeedbackResponse(it) },
            totalPages = info.totalPages,
            totalElements = info.totalElements,
            currentPage = info.currentPage
        )
    }
}