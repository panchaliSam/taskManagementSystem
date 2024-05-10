package com.example.taskmanagementsystem

import android.content.ContentValues.TAG
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagementsystem.data.Task
import com.example.taskmanagementsystem.data.TaskAdapter
import com.example.taskmanagementsystem.data.TaskDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.util.Log
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var taskNameEditText: EditText
    private lateinit var taskDescriptionEditText: EditText
    private lateinit var taskPriorityEditText: EditText
    private lateinit var taskDeadlineEditText: EditText
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize EditText fields and RecyclerView
        taskNameEditText = findViewById(R.id.task_name)
        taskDescriptionEditText = findViewById(R.id.task_des)
        taskPriorityEditText = findViewById(R.id.task_priority)
        taskDeadlineEditText = findViewById(R.id.task_deadline)
        recyclerView = findViewById(R.id.recyclerView)

        // Set up RecyclerView
        val adapter = TaskAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set click listeners for buttons
        findViewById<Button>(R.id.add).setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                Log.d(TAG, "Add button clicked")
                saveTask()
            }
        }
        findViewById<Button>(R.id.view).setOnClickListener {
            Log.d(TAG, "View button clicked")
            displayTasks()
        }

        findViewById<Button>(R.id.edit).setOnClickListener {
            Log.d(TAG, "Edit button clicked")
            updateTask()
        }

        findViewById<Button>(R.id.delete).setOnClickListener {
            Log.d(TAG, "Delete button clicked")
            deleteTask()
        }
    }

    private suspend fun saveTask() {
        val taskName = taskNameEditText.text.toString()
        val taskDescription = taskDescriptionEditText.text.toString()
        val taskPriority = taskPriorityEditText.text.toString().toInt()
        val taskDeadline = taskDeadlineEditText.text.toString() // Convert to Date format if needed

        if (taskName.isNotEmpty() && taskDescription.isNotEmpty() && taskPriority > 0 && taskDeadline.isNotEmpty()) {
            val task = Task(0, taskName, taskDescription, taskPriority, taskDeadline)
            val database = TaskDatabase.getInstance(this)

            database.taskDao().insertTasks(task)

            Log.d(TAG, "Saving task...")

            Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show()

            // Clear EditText fields
            taskNameEditText.text.clear()
            taskDescriptionEditText.text.clear()
            taskPriorityEditText.text.clear()
            taskDeadlineEditText.text.clear()
        } else {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayTasks() {
        CoroutineScope(Dispatchers.Main).launch {
            val database = TaskDatabase.getInstance(this@MainActivity)
            val tasks = database.taskDao().getAllTasks()

            Log.d(TAG, "Displaying tasks...")

            // Pass the list of tasks to the adapter constructor
            val adapter = TaskAdapter(tasks)
            recyclerView.adapter = adapter
        }
    }


    private fun updateTask() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.update_dialoge, null)
        dialogBuilder.setView(dialogView)

        val edtTaskId = dialogView.findViewById<EditText>(R.id.updateTaskId)
        val edtTaskName = dialogView.findViewById<EditText>(R.id.updateTaskName)
        val edtTaskDescription = dialogView.findViewById<EditText>(R.id.updateTaskDescription)
        val edtTaskPriority = dialogView.findViewById<EditText>(R.id.updateTaskPriority)
        val edtTaskDeadline = dialogView.findViewById<EditText>(R.id.updateTaskDeadline)

        dialogBuilder.setTitle("Update Task")
        dialogBuilder.setMessage("Enter data below")
        dialogBuilder.setPositiveButton("Update") { _, _ ->
            val taskId = edtTaskId.text.toString()
            val taskName = edtTaskName.text.toString()
            val taskDescription = edtTaskDescription.text.toString()
            val taskPriority = edtTaskPriority.text.toString().toInt()
            val taskDeadline = edtTaskDeadline.text.toString()

            // Check if input fields are not empty
            if (taskId.isNotEmpty() && taskName.isNotEmpty() && taskDescription.isNotEmpty() &&
                taskPriority > 0 && taskDeadline.isNotEmpty()
            ) {
                // Create a Task object with the updated values
                val task = Task(taskId.toInt(), taskName, taskDescription, taskPriority, taskDeadline)
                val database = TaskDatabase.getInstance(this)

                CoroutineScope(Dispatchers.IO).launch {
                    database.taskDao().updateTasks(task)
                }

                Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        dialogBuilder.create().show()
    }

    private fun deleteTask() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.delete_dialoge, null)
        dialogBuilder.setView(dialogView)

        val edtTaskId = dialogView.findViewById<EditText>(R.id.deleteTaskId)

        dialogBuilder.setTitle("Delete Task")
        dialogBuilder.setMessage("Enter ID below")
        dialogBuilder.setPositiveButton("Delete") { _, _ ->
            val taskId = edtTaskId.text.toString()

            // Check if input field is not empty
            if (taskId.isNotEmpty()) {
                val database = TaskDatabase.getInstance(this)

                CoroutineScope(Dispatchers.IO).launch {
                    database.taskDao().deleteTask(taskId.toInt())
                }

                Toast.makeText(this, "Task deleted successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Task ID cannot be blank", Toast.LENGTH_SHORT).show()
            }
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        dialogBuilder.create().show()
    }
}
