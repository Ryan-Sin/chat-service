package com.ryan.sionic.controller

import com.ryan.sionic.common.dto.SuccessResponseDto
import com.ryan.sionic.controller.dto.FeedbackRequestDto
import com.ryan.sionic.controller.dto.FeedbackResponseDto
import com.ryan.sionic.controller.dto.FeedbacksResponseDto
import com.ryan.sionic.controller.dto.UpdateFeedbackStatusRequestDto
import com.ryan.sionic.controller.mapper.FeedbackDtoMapper
import com.ryan.sionic.service.FeedbackService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/feedbacks")
class FeedbackController(
    private val feedbackService: FeedbackService,
    private val feedbackDtoMapper: FeedbackDtoMapper
) {

    @PostMapping
    @Operation(
        summary = "피드백 생성", description = "특정 대화에 대한 피드백을 생성합니다.",
        responses = [
            ApiResponse(
                responseCode = "200", description = "응답 정보",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = FeedbackResponseDto::class)
                    )
                ]
            ),
        ]
    )
    fun createFeedback(
        @AuthenticationPrincipal userDetails: UserDetails,
        @Valid @RequestBody dto: FeedbackRequestDto
    ): SuccessResponseDto<FeedbackResponseDto> {
        val command = feedbackDtoMapper.mapToCreateFeedbackCommand(userDetails.username, dto)
        val data = feedbackService.createFeedback(command)
        val result = feedbackDtoMapper.mapToFeedbackResponse(data)
        return SuccessResponseDto(data = result)
    }

    @GetMapping
    @Operation(
        summary = "피드백 목록 조회", description = "사용자의 피드백 목록을 조회합니다.",
        responses = [
            ApiResponse(
                responseCode = "200", description = "응답 정보",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = FeedbacksResponseDto::class)
                    )
                ]
            ),
        ]
    )
    fun getFeedbacks(
        @AuthenticationPrincipal userDetails: UserDetails,
        @RequestParam(required = false) isPositive: Boolean?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "DESC") sort: String
    ): SuccessResponseDto<FeedbacksResponseDto> {
        val command = feedbackDtoMapper.mapToGetFeedbacksCommand(
            userDetails.username, isPositive, page, size, sort
        )
        val data = feedbackService.getFeedbacks(command)
        val result = feedbackDtoMapper.mapToFeedbacksResponse(data)
        
        return SuccessResponseDto(data = result)
    }

    @GetMapping("/all")
    @Operation(
        summary = "모든 피드백 목록 조회", description = "관리자가 모든 사용자의 피드백 목록을 조회합니다.",
        responses = [
            ApiResponse(
                responseCode = "200", description = "응답 정보",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = FeedbacksResponseDto::class)
                    )
                ]
            ),
        ]
    )
    fun getAllFeedbacks(
        @AuthenticationPrincipal userDetails: UserDetails,
        @RequestParam(required = false) isPositive: Boolean?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "DESC") sort: String
    ): SuccessResponseDto<FeedbacksResponseDto> {
        val command = feedbackDtoMapper.mapToGetAllFeedbacksCommand(
            userDetails.username, isPositive, page, size, sort
        )
        val data = feedbackService.getAllFeedbacks(command)
        val result = feedbackDtoMapper.mapToFeedbacksResponse(data)
        
        return SuccessResponseDto(data = result)
    }

    @PatchMapping("/{feedbackId}/status")
    @Operation(
        summary = "피드백 상태 변경", description = "특정 피드백의 상태를 변경합니다.",
        responses = [
            ApiResponse(
                responseCode = "200", description = "응답 정보",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = FeedbackResponseDto::class)
                    )
                ]
            ),
        ]
    )
    fun updateFeedbackStatus(
        @AuthenticationPrincipal userDetails: UserDetails,
        @PathVariable feedbackId: Long,
        @Valid @RequestBody dto: UpdateFeedbackStatusRequestDto
    ): SuccessResponseDto<FeedbackResponseDto> {
        val command = feedbackDtoMapper.mapToUpdateFeedbackStatusCommand(
            userDetails.username, feedbackId, dto
        )
        val data = feedbackService.updateFeedbackStatus(command)
        val result = feedbackDtoMapper.mapToFeedbackResponse(data)
        
        return SuccessResponseDto(data = result)
    }
}