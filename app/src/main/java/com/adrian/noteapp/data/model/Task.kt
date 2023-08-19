package com.adrian.noteapp.data.model

data class Task(
    val id: Int = -1,
    val title: String,
    val desc: String,
    val color: Color
)