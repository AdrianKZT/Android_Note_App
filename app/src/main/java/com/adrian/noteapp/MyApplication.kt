package com.adrian.noteapp

import android.app.Application
import com.adrian.noteapp.data.repository.TaskRepository

class MyApplication : Application() {
    val repo = TaskRepository()
}