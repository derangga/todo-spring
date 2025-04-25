package com.todo.api.service

import com.todo.api.dto.TodoReq
import com.todo.api.dto.UpdateTodoReq
import com.todo.api.entity.Todo
import com.todo.api.repository.TodoRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class TodoService(
    private val repo: TodoRepository
) {
    private val logger by lazy { LoggerFactory.getLogger(AccountService::class.java) }

    fun createTodo(userId: Long, req: TodoReq): Result<Unit> = runCatching {
        repo.save(
            Todo(
                userId = userId,
                title = req.title,
                description = req.description
            )
        )

        Unit
    }.onFailure {
        logger.error("failed create todo: ${it.message}")
    }

    fun getAllTodo(userId: Long): List<Todo> {
        return repo.findAllNonDelete(userId)
    }

    fun detailTodo(userId: Long, todoId: Long): Result<Todo> = runCatching {
        val result = repo.findSingle(userId, todoId).orElseThrow { error("todo not found") }

        result
    }.onFailure {
        logger.error("failed get todo: ${it.message}")
    }

    fun updateTodo(userId: Long, todoId: Long, req: UpdateTodoReq): Result<Todo> = runCatching {
        val result = repo.findSingle(userId, todoId).orElseThrow { error("todo not found") }

        req.title?.takeIf { it.isNotEmpty() }?.let { result.title = it }
        req.description?.takeIf { it.isNotEmpty() }?.let { result.description = it }
        req.completed?.let { result.completed = it }
        result.updatedAt = Instant.now()

        repo.save(result)

        result
    }.onFailure {
        logger.error("failed update todo: ${it.message}")
    }

    fun deleteTodo(userId: Long, todoId: Long): Result<Unit> = runCatching {
        repo.deleteSingle(userId, todoId)
    }.onFailure {
        logger.error("failed delete todo: ${it.message}")
    }
}