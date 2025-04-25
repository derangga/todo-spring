package com.todo.api.dto

import jakarta.validation.constraints.Min

data class TodoReq(
    @Min(1) val title: String,
    @Min(1) val description: String,
)

data class UpdateTodoReq(
    val title: String?,
    val description: String?,
    val completed: Boolean?
)