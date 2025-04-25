package com.todo.api.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthResponse(@JsonProperty("access_token") val accessToken: String)