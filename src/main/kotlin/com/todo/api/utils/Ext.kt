package com.todo.api.utils

import org.springframework.security.core.Authentication

fun <T> Result<T>.getExMessage(): String {
    return this.exceptionOrNull()?.message.orEmpty()
}

fun Authentication.getUserId(): Long {
    return this.principal as Long
}