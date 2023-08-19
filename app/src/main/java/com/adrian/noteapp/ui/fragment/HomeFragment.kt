package com.adrian.noteapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentController
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.adrian.noteapp.R
import com.adrian.noteapp.databinding.FragmentHomeBinding
import com.adrian.noteapp.ui.adapter.TaskAdapter
import com.adrian.noteapp.ui.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: TaskAdapter
    private lateinit var navController: NavController
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.Factory
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = NavHostFragment.findNavController(this)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.tasks.observe(viewLifecycleOwner) {
            adapter.setNewItems(it)
        }

        binding.fabAdd.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToAddTask()
            navController.navigate(action)
        }

        setupAdapter()

        setFragmentResultListener("from_add_task_fragment") { _, result ->
            val refresh = result.getBoolean("refresh")
            if (refresh) {
                viewModel.fetchTasks()
            }
        }

        setFragmentResultListener("from_update_task_fragment") { _, result ->
            val refresh = result.getBoolean("refresh")
            if (refresh) {
                viewModel.fetchTasks()
            }
        }

    }

    fun setupAdapter() {
        adapter = TaskAdapter(emptyList()) {
            val action = HomeFragmentDirections.actionHomeToShowTask(it.id)
            navController.navigate(action)

        }

        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvFragment.adapter = adapter
        binding.rvFragment.layoutManager = layoutManager
    }
}

//val action = HomeFragmentDirections.actionHomeToShowTask()