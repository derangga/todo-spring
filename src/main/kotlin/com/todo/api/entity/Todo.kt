package com.todo.api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table
data class Todo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0,
    @Column(name = "user_id") val userId: Long,
    var title: String,
    var description: String,
    var completed: Boolean = false,
    @Column(name = "created_at") val createdAt: Instant = Instant.now(),
    @Column(name = "updated_at") var updatedAt: Instant = Instant.now(),
    @Column(name = "deleted_at") val deletedAt: Instant? = null
)