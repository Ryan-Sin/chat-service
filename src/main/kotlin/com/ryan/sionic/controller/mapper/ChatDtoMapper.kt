package com.ryan.sionic.controller.mapper

import com.ryan.sionic.controller.dto.ChatRequestDto
import com.ryan.sionic.controller.dto.ChatResponseDto
import com.ryan.sionic.controller.dto.ThreadResponseDto
import com.ryan.sionic.controller.dto.ThreadsResponseDto
import com.ryan.sionic.service.command.CreateChatCommand
import com.ryan.sionic.service.command.DeleteThreadCommand
import com.ryan.sionic.service.command.GetAllChatsCommand
import com.ryan.sionic.service.command.GetChatsCommand
import com.ryan.sionic.service.info.ChatInfo
import com.ryan.sionic.service.info.ThreadInfo
import com.ryan.sionic.service.info.ThreadsInfo
import org.springframework.stereotype.Component

@Component
class ChatDtoMapper {
    fun mapToCreateChatCommand(email: String, dto: ChatRequestDto): CreateChatCommand {
        return CreateChatCommand(
            email = email,
            question = dto.question,
            isStreaming = dto.isStreaming,
            model = dto.model
        )
    }

    fun mapToGetChatsCommand(email: String, page: Int, size: Int, sort: String): GetChatsCommand {
        return GetChatsCommand(
            email = email,
            page = page,
            size = size,
            sort = sort
        )
    }

    fun mapToGetAllChatsCommand(email: String, page: Int, size: Int, sort: String): GetAllChatsCommand {
        return GetAllChatsCommand(
            email = email,
            page = page,
            size = size,
            sort = sort
        )
    }

    fun mapToDeleteThreadCommand(email: String, threadId: Long): DeleteThreadCommand {
        return DeleteThreadCommand(
            email = email,
            threadId = threadId
        )
    }

    fun mapToChatResponse(info: ChatInfo): ChatResponseDto {
        return ChatResponseDto(
            id = info.id,
            question = info.question,
            answer = info.answer,
            createdAt = info.createdAt
        )
    }

    fun mapToThreadResponse(info: ThreadInfo): ThreadResponseDto {
        return ThreadResponseDto(
            id = info.id,
            createdAt = info.createdAt,
            chats = info.chats.map { mapToChatResponse(it) }
        )
    }

    fun mapToThreadsResponse(info: ThreadsInfo): ThreadsResponseDto {
        return ThreadsResponseDto(
            threads = info.threads.map { mapToThreadResponse(it) },
            totalPages = info.totalPages,
            totalElements = info.totalElements,
            currentPage = info.currentPage
        )
    }
}