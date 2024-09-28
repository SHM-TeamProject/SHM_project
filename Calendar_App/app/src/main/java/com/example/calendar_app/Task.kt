package com.example.calendar_app

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val startTime: String,
    val endTime: String,
    val content: String,
    val date: String
)