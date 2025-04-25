package com.todo.api.service

import com.todo.api.dto.LoginReq
import com.todo.api.dto.RegisterReq
import com.todo.api.entity.User
import com.todo.api.repository.AccountRepository
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val repo: AccountRepository,
    private val passwordEncoder: PasswordEncoder
) {

    private val logger by lazy { LoggerFactory.getLogger(AccountService::class.java) }

    fun register(req: RegisterReq): Result<User> = runCatching {
        require(repo.findByEmail(req.email) == null) { "email taken" }

        val user = User(
            name = req.name,
            email = req.email,
            password = passwordEncoder.encode(req.password)
        )

        repo.save(user)
    }.onFailure {
        logger.error("failed register user: ${it.message}")
    }

    fun login(req: LoginReq): Result<User> = runCatching {
        val defaultError = "Invalid Credential"
        val user = repo.findByEmail(req.email) ?: error(defaultError)

        check(passwordEncoder.matches(req.password, user.password)) { defaultError }

        user
    }.onFailure {
        logger.error("failed login: ${it.message}")
    }

    fun getUserById(id: Long): Result<User> = runCatching {
        repo.findById(id).orElseThrow { error("Invalid user id") }
    }.onFailure {
        logger.error("failed get user: ${it.message}")
    }
}