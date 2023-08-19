package com.adrian.noteapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.adrian.noteapp.MyApplication
import com.adrian.noteapp.data.model.Color
import com.adrian.noteapp.data.model.Task
import com.adrian.noteapp.data.repository.TaskRepository

class UpdateTaskViewModel(
    private val repository: TaskRepository,
) : ViewModel() {
    val title: MutableLiveData<String> = MutableLiveData()
    val desc: MutableLiveData<String> = MutableLiveData()
    val finished: MutableLiveData<Pair<Boolean, String>> = MutableLiveData(Pair(false, "Finish"))
    val error: MutableLiveData<String> = MutableLiveData("")
    val color: MutableLiveData<Color> = MutableLiveData()
    val task: MutableLiveData<Task> = MutableLiveData()


    private var id: Int = -1

    fun init(id: Int) {
        this.id = id
        val res = repository.getTask(id)
        Log.d("debugging", res.toString())
        res?.let {
            title.value = it.title
            desc.value = it.desc

        }
    }

    fun deleteTask() {
        repository.deleteTask(id)
        finished.value = Pair(true, "Task deleted successfully!")
    }

    fun updateTask() {
        if (title.value != null && desc.value != null && color.value != null) {
            repository.updateTask(
                Task(id = id, title = title.value!!, desc = desc.value!!, color = color.value!!)
            )
            finished.value  = Pair(true, "Task updated successfully!")
        } else if (title.value == null) {
            error.value = "Title cannot be empty!"
        } else if (desc.value == null) {
            error.value = "Description cannot be empty!"
        } else if (color.value == null) {
            error.value = "Please select a color"
        } else {
            error.value = "Oops! Are they any empty input ?"
        }
    }

    fun onGreenClicked() {
        color.value = Color.GREEN
    }

    fun onRedClicked() {
        color.value = Color.RED
    }

    fun onBlueClicked() {
        color.value = Color.BLUE
    }

    fun onYellowClicked() {
        color.value = Color.YELLOW
    }

    fun onPurpleClicked() {
        color.value = Color.PURPLE
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val myRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication).repo
                UpdateTaskViewModel(
                    myRepository,
                )
            }
        }
    }

}