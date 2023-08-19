package com.adrian.noteapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.adrian.noteapp.MyApplication
import com.adrian.noteapp.data.model.Task
import com.adrian.noteapp.data.repository.TaskRepository

class HomeViewModel(
    private val repository: TaskRepository
) : ViewModel() {
    val tasks: MutableLiveData<List<Task>> = MutableLiveData()
    val isEmpty: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        fetchTasks()
    }

    fun fetchTasks() {
        val res = repository.getTask()
        tasks.value = res
        isEmpty.value = res.isEmpty()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val myRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication).repo
                HomeViewModel(
                    myRepository,
                )
            }
        }
    }
}