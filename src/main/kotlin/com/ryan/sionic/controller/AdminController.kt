package com.ryan.sionic.controller

import com.ryan.sionic.common.dto.SuccessResponseDto
import com.ryan.sionic.controller.dto.UserActivityResponseDto
import com.ryan.sionic.controller.mapper.AdminDtoMapper
import com.ryan.sionic.service.AdminService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/admin")
class AdminController(
    private val adminService: AdminService,
    private val adminDtoMapper: AdminDtoMapper
) {

    @GetMapping("/activity")
    @Operation(
        summary = "사용자 활동 기록 조회", description = "회원가입, 로그인, 대화 생성 수를 조회합니다.",
        responses = [
            ApiResponse(
                responseCode = "200", description = "응답 정보",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = UserActivityResponseDto::class)
                    )
                ]
            ),
        ]
    )
    fun getUserActivity(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate?
    ): SuccessResponseDto<UserActivityResponseDto> {
        val command = adminDtoMapper.mapToGetUserActivityCommand(date)
        val data = adminService.getUserActivity(command)
        val result = adminDtoMapper.mapToUserActivityResponse(data)
        
        return SuccessResponseDto(data = result)
    }

    @GetMapping("/report")
    @Operation(
        summary = "보고서 생성", description = "모든 사용자의 대화 목록을 CSV 형태로 생성합니다.",
        responses = [
            ApiResponse(
                responseCode = "200", description = "CSV 파일",
                content = [
                    Content(
                        mediaType = "text/csv"
                    )
                ]
            ),
        ]
    )
    fun generateReport(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate?
    ): ResponseEntity<ByteArray> {
        val command = adminDtoMapper.mapToGenerateReportCommand(date)
        val report = adminService.generateReport(command)
        
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val fileName = "chat_report_${date?.format(formatter) ?: LocalDate.now().format(formatter)}.csv"
        
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType("text/csv"))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"$fileName\"")
            .body(report)
    }
}