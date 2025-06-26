package com.todo.api.controller

import com.todo.api.dto.AuthResponse
import com.todo.api.dto.BaseResponse
import com.todo.api.dto.ErrorResponse
import com.todo.api.dto.LoginReq
import com.todo.api.dto.RegisterReq
import com.todo.api.dto.ResponseBuilder
import com.todo.api.security.JwtService
import com.todo.api.service.AuthService
import com.todo.api.utils.getExMessage
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val svc: AuthService,
    private val jwt: JwtService
) {
    @PostMapping("/auth/register")
    fun register(@Valid @RequestBody request: RegisterReq): ResponseEntity<*> {
        val result = svc.register(request)
        if (result.isFailure) {
            return ResponseBuilder.build(
                HttpStatus.UNPROCESSABLE_ENTITY,
                ErrorResponse(result.getExMessage())
            )
        }
        return ResponseBuilder.build(HttpStatus.CREATED, BaseResponse("success register"))
    }

    @PostMapping("/auth/login")
    fun login(@Valid @RequestBody request: LoginReq): ResponseEntity<*> {
        val result = svc.login(request)
        val userId = result.getOrNull()?.id
        if (result.isFailure || userId == null) {
            return ResponseBuilder.build(
                HttpStatus.UNPROCESSABLE_ENTITY,
                ErrorResponse(result.getExMessage())
            )
        }
        val token = userId.let(jwt::generate)
        return ResponseEntity.ok(BaseResponse(AuthResponse(token)))
    }
}