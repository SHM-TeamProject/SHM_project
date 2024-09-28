package com.example.calendar_app

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title: String,
    val startTime: String,
    val endTime: String,
    var content: String,
    val date: String

)