package com.ryan.sionic.controller

import com.ryan.sionic.common.dto.SuccessResponseDto
import com.ryan.sionic.controller.dto.ChatRequestDto
import com.ryan.sionic.controller.dto.ChatResponseDto
import com.ryan.sionic.controller.dto.ThreadsResponseDto
import com.ryan.sionic.controller.mapper.ChatDtoMapper
import com.ryan.sionic.service.ChatService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chats")
class ChatController(
    private val chatService: ChatService,
    private val chatDtoMapper: ChatDtoMapper
) {

    @PostMapping
    @Operation(
        summary = "대화 생성", description = "질문을 입력받아 AI 응답을 생성합니다.",
        responses = [
            ApiResponse(
                responseCode = "200", description = "응답 정보",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ChatResponseDto::class)
                    )
                ]
            ),
        ]
    )
    fun createChat(
        @AuthenticationPrincipal userDetails: UserDetails,
        @Valid @RequestBody dto: ChatRequestDto
    ): SuccessResponseDto<ChatResponseDto> {
        val command = chatDtoMapper.mapToCreateChatCommand(userDetails.username, dto)
        val data = chatService.createChat(command)
        val result = chatDtoMapper.mapToChatResponse(data)
        return SuccessResponseDto(data = result)
    }

    @GetMapping
    @Operation(
        summary = "대화 목록 조회", description = "사용자의 대화 목록을 조회합니다.",
        responses = [
            ApiResponse(
                responseCode = "200", description = "응답 정보",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ThreadsResponseDto::class)
                    )
                ]
            ),
        ]
    )
    fun getChats(
        @AuthenticationPrincipal userDetails: UserDetails,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "DESC") sort: String
    ): SuccessResponseDto<ThreadsResponseDto> {
        val command = chatDtoMapper.mapToGetChatsCommand(
            userDetails.username, page, size, sort
        )
        val data = chatService.getChats(command)
        val result = chatDtoMapper.mapToThreadsResponse(data)
        
        return SuccessResponseDto(data = result)
    }

    @GetMapping("/all")
    @Operation(
        summary = "모든 대화 목록 조회", description = "관리자가 모든 사용자의 대화 목록을 조회합니다.",
        responses = [
            ApiResponse(
                responseCode = "200", description = "응답 정보",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ThreadsResponseDto::class)
                    )
                ]
            ),
        ]
    )
    fun getAllChats(
        @AuthenticationPrincipal userDetails: UserDetails,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "DESC") sort: String
    ): SuccessResponseDto<ThreadsResponseDto> {
        val command = chatDtoMapper.mapToGetAllChatsCommand(
            userDetails.username, page, size, sort
        )
        val data = chatService.getAllChats(command)
        val result = chatDtoMapper.mapToThreadsResponse(data)
        
        return SuccessResponseDto(data = result)
    }

    @DeleteMapping("/threads/{threadId}")
    @Operation(
        summary = "스레드 삭제", description = "특정 스레드를 삭제합니다.",
        responses = [
            ApiResponse(
                responseCode = "200", description = "응답 정보",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = SuccessResponseDto::class)
                    )
                ]
            ),
        ]
    )
    fun deleteThread(
        @AuthenticationPrincipal userDetails: UserDetails,
        @PathVariable threadId: Long
    ): SuccessResponseDto<Void> {
        val command = chatDtoMapper.mapToDeleteThreadCommand(userDetails.username, threadId)
        chatService.deleteThread(command)
        
        return SuccessResponseDto()
    }
}