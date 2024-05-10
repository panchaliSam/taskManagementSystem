package com.example.taskmanagementsystem.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "TaskTable")
data class Task(
    @PrimaryKey(autoGenerate = true) val taskId: Int,
    val taskName: String,
    val taskDes: String,
    val priority: Int,
    val deadLine: Date
)
