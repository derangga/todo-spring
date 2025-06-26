package com.todo.api.controller

import com.todo.api.dto.BaseResponse
import com.todo.api.dto.ErrorResponse
import com.todo.api.dto.ResponseBuilder
import com.todo.api.dto.UserResponse
import com.todo.api.service.AccountService
import com.todo.api.utils.getExMessage
import com.todo.api.utils.getUserId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class AccountController(
    private val svc: AccountService
) {

    @GetMapping("/me")
    fun getUser(auth: Authentication): ResponseEntity<*> {
        val result = svc.getUserById(auth.getUserId())
        val user = result.getOrNull()
        if (result.isFailure || user == null) {
            return ResponseBuilder.build(
                HttpStatus.UNPROCESSABLE_ENTITY,
                ErrorResponse(result.getExMessage())
            )
        }
        return ResponseEntity.ok(BaseResponse(UserResponse.build(user)))
    }
}