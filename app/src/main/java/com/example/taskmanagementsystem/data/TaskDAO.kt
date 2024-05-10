package com.example.taskmanagementsystem.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDAO {
    @Query("SELECT * FROM TaskTable ORDER BY taskId ASC")
    fun getAllTasks(): List<Task>

    @Insert
    fun insertTasks(book: Task)

    @Update
    fun updateTasks(book: Task)

    @Query("DELETE FROM TaskTable WHERE taskId = :taskId")
    fun deleteTask(taskId: Int)
}
