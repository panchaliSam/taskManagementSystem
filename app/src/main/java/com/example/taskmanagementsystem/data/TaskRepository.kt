package com.example.taskmanagementsystem.data

class TaskRepository(private val taskDao: TaskDAO) {
    suspend fun insertTask(task: Task) {
        taskDao.insertTasks(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTasks(task)
    }

    suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTask(taskId)
    }

    suspend fun getAllTasks(): List<Task> {
        return taskDao.getAllTasks()
    }
}
