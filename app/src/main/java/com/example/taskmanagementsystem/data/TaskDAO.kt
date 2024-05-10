package com.example.taskmanagementsystem.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDAO {
    @Query("SELECT * FROM TaskTable ORDER BY taskId ASC")
    suspend fun getAllTasks(): List<Task>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTasks(book: Task)

    @Update
    suspend fun updateTasks(book: Task)

    @Query("DELETE FROM TaskTable WHERE taskId = :taskId")
    suspend fun deleteTask(taskId: Int)
}
