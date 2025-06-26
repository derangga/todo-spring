package com.todo.api.controller

import com.todo.api.dto.BaseResponse
import com.todo.api.dto.ErrorResponse
import com.todo.api.dto.ResponseBuilder
import com.todo.api.dto.TodoReq
import com.todo.api.dto.TodoResponse
import com.todo.api.dto.UpdateTodoReq
import com.todo.api.service.TodoService
import com.todo.api.utils.getExMessage
import com.todo.api.utils.getUserId
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/todos")
class TodoController(
    private val svc: TodoService
) {
    @PostMapping
    fun createTodo(auth: Authentication, @Valid @RequestBody req: TodoReq): ResponseEntity<*> {
        val result = svc.createTodo(auth.getUserId(), req)
        if (result.isFailure) {
            return ResponseBuilder.build(HttpStatus.UNPROCESSABLE_ENTITY, ErrorResponse(result.getExMessage()))
        }

        return ResponseBuilder.build(HttpStatus.CREATED, BaseResponse("todo created"))
    }

    @GetMapping
    fun getAllTodo(auth: Authentication): ResponseEntity<*> {
        val result = svc.getAllTodo(auth.getUserId())
        val serialize = result.map { TodoResponse.build(it) }
        return ResponseEntity.ok(BaseResponse(serialize))
    }

    @GetMapping("/{id}")
    fun detailTodo(auth: Authentication, @PathVariable id: Long): ResponseEntity<*> {
        val result = svc.detailTodo(auth.getUserId(), id)
        val todo = result.getOrNull()
        if (result.isFailure || todo == null) {
            return ResponseBuilder.build(HttpStatus.UNPROCESSABLE_ENTITY, ErrorResponse(result.getExMessage()))
        }

        return ResponseEntity.ok(BaseResponse(TodoResponse.build(todo)))
    }

    @PatchMapping("/{id}")
    fun updateTodo(
        auth: Authentication,
        @PathVariable id: Long,
        @Valid @RequestBody req: UpdateTodoReq
    ): ResponseEntity<*> {
        val result = svc.updateTodo(auth.getUserId(), id, req)
        val todo = result.getOrNull()
        if (result.isFailure || todo == null) {
            return ResponseBuilder.build(HttpStatus.UNPROCESSABLE_ENTITY, ErrorResponse(result.getExMessage()))
        }

        return ResponseEntity.ok(BaseResponse(TodoResponse.build(todo)))
    }

    @DeleteMapping("/{id}")
    fun deleteTodo(auth: Authentication, @PathVariable id: Long): ResponseEntity<*> {
        val result = svc.deleteTodo(auth.getUserId(), id)
        if (result.isFailure) {
            return ResponseBuilder.build(HttpStatus.UNPROCESSABLE_ENTITY, ErrorResponse(result.getExMessage()))
        }

        return ResponseEntity.ok(BaseResponse("todo $id deleted"))
    }
}