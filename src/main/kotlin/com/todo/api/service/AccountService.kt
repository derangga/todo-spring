package com.todo.api.service

import com.todo.api.entity.User
import com.todo.api.repository.AccountRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val repo: AccountRepository
) {

    private val logger by lazy { LoggerFactory.getLogger(AccountService::class.java) }

    fun getUserById(id: Long): Result<User> = runCatching {
        repo.findById(id).orElseThrow { error("Invalid user id") }
    }.onFailure {
        logger.error("failed get user: ${it.message}")
    }
}