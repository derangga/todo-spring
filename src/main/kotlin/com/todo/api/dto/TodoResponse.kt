package com.todo.api.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.todo.api.entity.Todo
import java.time.Instant

data class TodoResponse(
    val id: Long,
    val title: String,
    val description: String,
    val completed: Boolean,
    @JsonProperty("created_at") val createdAt: Instant,
    @JsonProperty("updated_at") val updatedAt: Instant,
) {
    companion object {
        fun build(todo: Todo): TodoResponse {
            return TodoResponse(
                id = todo.id,
                title = todo.title,
                description = todo.description,
                completed = todo.completed,
                createdAt = todo.createdAt,
                updatedAt = todo.updatedAt
            )
        }
    }
}