package com.todo.api.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

data class BaseResponse<T>(
    val data: T
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse(
    val errors: String,
    @JsonProperty("http_status") val httpStatus: Int? = null,
)

