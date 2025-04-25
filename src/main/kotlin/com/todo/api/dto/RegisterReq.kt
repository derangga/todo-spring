package com.todo.api.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min

data class RegisterReq(@Min(2) val name: String, @Email val email: String,@Min(6) val password: String)