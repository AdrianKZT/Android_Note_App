package com.adrian.noteapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.adrian.noteapp.MyApplication
import com.adrian.noteapp.data.model.Task
import com.adrian.noteapp.data.repository.TaskRepository
import kotlinx.coroutines.launch
import kotlin.math.log

class ShowTaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {
    val task: MutableLiveData<Task> = MutableLiveData()

    fun getTask(id: Int){
        val res = repository.getTask(id)
        Log.d("debugging", res.toString())
        res?.let {
            task.value = it
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val myRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication).repo
                ShowTaskViewModel(
                    myRepository,
                )
            }
        }
    }
}