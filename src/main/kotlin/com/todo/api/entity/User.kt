package com.todo.api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    val name: String,
    @Column(unique = true) val email: String,
    val password: String,
    @Column(name = "created_at") val createdAt: Instant = Instant.now(),
    @Column(name = "updated_at") val updatedAt: Instant = Instant.now(),
    @Column(name = "deleted_at") val deletedAt: Instant? = null
)