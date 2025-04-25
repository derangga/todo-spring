package com.todo.api.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.todo.api.entity.User
import java.time.Instant

data class UserResponse(
    val id: Long,
    val name: String,
    val email: String,
    @JsonProperty("created_at") val createdAt: Instant
) {
    companion object {
        fun build(user: User): UserResponse {
            return UserResponse(
                id = user.id ?: 0, name = user.name, email = user.email, createdAt = user.createdAt
            )
        }
    }
}