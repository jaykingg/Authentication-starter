package org.example.sample.core

import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.ServerWebInputException

@RestControllerAdvice
@Order(-2)
class ExceptionsHandler {
    @ExceptionHandler(ResponseStatusException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleResponseStatusExceptions(ex: ResponseStatusException): ErrorResponse {
        return ErrorResponse(
            code = ex.statusCode.value(),
            error = ex.reason ?: "응답 오류가 발생했습니다.",
            message = ex.message
        )
    }

    @ExceptionHandler(WebExchangeBindException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: WebExchangeBindException): ErrorResponse {
        val errorMessage = ex.bindingResult.fieldErrors.joinToString { it.defaultMessage ?: "" }
        return ErrorResponse(
            code = HttpStatus.BAD_REQUEST.value(),
            error = "잘못된 요청입니다.",
            message = errorMessage
        )
    }

    @ExceptionHandler(ServerWebInputException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: ServerWebInputException): ErrorResponse {
        return ErrorResponse(
            code = HttpStatus.BAD_REQUEST.value(),
            error = "잘못된 요청입니다.",
            message = ex.message
        )
    }

    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequest(ex: BadRequestException, exchange: ServerWebExchange): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            code = HttpStatus.BAD_REQUEST.value(),
            error = "잘못된 요청입니다.",
            message = ex.message
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFound(ex: NotFoundException, exchange: ServerWebExchange): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            code = HttpStatus.NOT_FOUND.value(),
            error = "리소스를 찾을 수 없습니다.",
            message = ex.message
        )
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(InternalServerException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleInternalError(ex: InternalServerException, exchange: ServerWebExchange): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            code = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = "서버에러가 발생했습니다.",
            message = ex.message
        )
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

}