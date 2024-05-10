package com.example.taskmanagementsystem.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagementsystem.R

class TaskAdapter(private val tasks: List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskId: TextView = itemView.findViewById(R.id.textViewTaskId)
        val taskName: TextView = itemView.findViewById(R.id.textViewTaskName)
        val taskDescription: TextView = itemView.findViewById(R.id.textViewTaskDescription)
        val taskPriority: TextView = itemView.findViewById(R.id.textViewTaskPriority)
        val taskDeadline: TextView = itemView.findViewById(R.id.textViewTaskDeadline)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.custom_list, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = tasks[position]
        holder.taskId.text = currentTask.taskId.toString()
        holder.taskName.text = currentTask.taskName
        holder.taskDescription.text = currentTask.taskDes
        holder.taskPriority.text = currentTask.priority.toString()
        holder.taskDeadline.text = currentTask.deadLine.toString() // Format date as needed
    }

    override fun getItemCount() = tasks.size
}
