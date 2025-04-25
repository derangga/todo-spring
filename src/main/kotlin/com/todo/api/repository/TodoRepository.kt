package com.todo.api.repository

import com.todo.api.entity.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Transactional
@Repository
interface TodoRepository : JpaRepository<Todo, Long> {
    @Query("SELECT id, user_id, title, description, completed, created_at, updated_at, deleted_at " +
            "FROM todo WHERE user_id=:userId AND deleted_at IS NULL", nativeQuery = true)
    fun findAllNonDelete(userId: Long): List<Todo>

    @Query("SELECT id, user_id, title, description, completed, created_at, updated_at, deleted_at " +
            "FROM todo WHERE id=:todoId AND user_id=:userId AND deleted_at IS NULL", nativeQuery = true)
    fun findSingle(userId: Long, todoId: Long): Optional<Todo>

    @Modifying
    @Query("UPDATE todo SET deleted_at=NOW() WHERE id=:todoId AND user_id=:userId", nativeQuery = true)
    fun deleteSingle(userId: Long, todoId: Long)
}