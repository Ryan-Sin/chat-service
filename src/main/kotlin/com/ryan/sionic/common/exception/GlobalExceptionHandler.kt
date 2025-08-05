package com.ryan.sionic.common.exception

import com.ryan.sionic.common.dto.ErrorResponseDto
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.stream.Collectors

@RestControllerAdvice
class GlobalExceptionHandler {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(
        exception: Exception,
        httpServletRequest: HttpServletRequest
    ): ResponseEntity<ErrorResponseDto> {
        log.error("Exception [Path]: {} [Message]: {}", httpServletRequest.requestURI, exception.message)
        return ResponseEntity(
            ErrorResponseDto(message = CommonErrorMessage.INTERNAL_SERVER_ERROR),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(HttpCommonException::class)
    fun handleCommonException(
        httpCommonException: HttpCommonException,
        httpServletRequest: HttpServletRequest
    ): ResponseEntity<ErrorResponseDto> {
        log.debug(
            "HttpCommonException [Path]: {} [Status]: {} [ClientErrorMessage]: {} [ServerErrorMessage]: {}",
            httpServletRequest.requestURI,
            httpCommonException.status,
            httpCommonException.clientErrorMessage,
            httpCommonException.serverErrorMessage
        )

        return ResponseEntity(
            ErrorResponseDto(message = httpCommonException.clientErrorMessage),
            httpCommonException.status
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(
        exception: MethodArgumentNotValidException,
        httpServletRequest: HttpServletRequest
    ): ResponseEntity<ErrorResponseDto> {
        val errorMessages = exception.bindingResult
            .allErrors
            .stream()
            .map { error: ObjectError ->
                if (error is FieldError) {
                    return@map error.field + ": " + error.getDefaultMessage()
                } else {
                    return@map error.defaultMessage
                }
            }
            .collect(Collectors.toList())

        log.error(
            "MethodArgumentNotValidException [Path]: {} [Message]: {}",
            httpServletRequest.requestURI,
            errorMessages
        )

        return ResponseEntity(ErrorResponseDto(message = errorMessages), HttpStatus.BAD_REQUEST)
    }
    

}