package com.todo.api.dto

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object ResponseBuilder {
     fun <T> build(status: HttpStatus, body: T): ResponseEntity<T> {
         return ResponseEntity.status(status).body(body)
     }
}