package com.adrian.noteapp.data.repository

import com.adrian.noteapp.data.model.Task

class TaskRepository {
    private var counter = 0
    private val tasks: MutableMap<Int, Task> = mutableMapOf()

    fun getTask(): List<Task> {
        return tasks.values.toList()
    }

    fun getTask(id: Int): Task? {
        return tasks[id]
    }

    fun addTask(task: Task) {
        counter++
        tasks[counter] = task.copy(id = counter)
    }

    fun deleteTask(id: Int) {
        tasks.remove(id)
    }

    fun updateTask(task: Task) {
        tasks[task.id] = task
    }
}