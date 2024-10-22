package com.example.calendar_app

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("UPDATE tasks SET title = :title, content = :content WHERE id = :taskId")
    suspend fun update(taskId: Int, title: String, content: String)

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<Task>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Int): Task?

    @Query("SELECT * FROM tasks WHERE date = :date")
    suspend fun getTaskByDate(date: String): List<Task?>

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()
}