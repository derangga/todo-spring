package com.todo.api.repository

import com.todo.api.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}