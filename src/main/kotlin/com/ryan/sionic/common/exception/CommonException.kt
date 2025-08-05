package com.ryan.sionic.common.exception

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus

@JsonInclude(JsonInclude.Include.NON_NULL)
data class HttpCommonException(
    @Schema(description = "HTTP 상태 코드", example = "404")
    val status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    @Schema(description = "클라이언트 에러 메시지", example = "XXX 찾을 수 없습니다.")
    val clientErrorMessage: String? = null,
    @Schema(description = "서버 에러 메시지", example = "XXX 찾을 수 없습니다.")
    val serverErrorMessage: String? = null
) : RuntimeException()