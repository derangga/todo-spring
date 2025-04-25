package com.todo.api.controller.error

import com.todo.api.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorControllerHandler {

    @ExceptionHandler(value = [HttpMessageNotReadableException::class])
    fun httpReadableException(ex: HttpMessageNotReadableException): ResponseEntity<*> {
        val status = HttpStatus.BAD_REQUEST
        return ResponseEntity.status(status).body(
            ErrorResponse("Invalid Request", status.value())
        )
    }
}