package com.adrian.noteapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.adrian.noteapp.data.model.Task
import com.adrian.noteapp.databinding.LayoutTasksItemBinding

class TaskAdapter(
    var tasks: List<Task>,
    val onItemClicked: (Task) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            LayoutTasksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val taskItemViewHolder = holder as TaskItemViewHolder
        val task = tasks[position]

        taskItemViewHolder.binding.run{
            tvTitle.text = task.title
            tvDesc.text = task.desc

            llTask.setOnClickListener{
                onItemClicked(task)
            }

            cvTaskRoot.setCardBackgroundColor(
                ContextCompat.getColor(
                    root.context,
                    task.color.rgb
                )
            )
        }
    }

    fun setNewItems(task: List<Task>){
        this.tasks = task
        notifyDataSetChanged()
    }

    class TaskItemViewHolder(val binding: LayoutTasksItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}