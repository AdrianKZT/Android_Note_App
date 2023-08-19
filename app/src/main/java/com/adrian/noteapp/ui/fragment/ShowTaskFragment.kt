package com.adrian.noteapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.adrian.noteapp.R
import com.adrian.noteapp.databinding.FragmentShowTaskBinding
import com.adrian.noteapp.ui.adapter.TaskAdapter
import com.adrian.noteapp.ui.viewModel.ShowTaskViewModel

class ShowTaskFragment : Fragment() {

    private lateinit var binding: FragmentShowTaskBinding
    private lateinit var navController: NavController

    private val viewModel: ShowTaskViewModel by viewModels {
        ShowTaskViewModel.Factory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShowTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = NavHostFragment.findNavController(this)
        val navArgs: ShowTaskFragmentArgs by navArgs()
        viewModel.getTask(navArgs.taskId)
        viewModel.task.observe(viewLifecycleOwner){
            binding.run {
                tvShowTitle.text = it.title
                tvShowDesc.text = it.desc
                llShowTask.setBackgroundColor(ContextCompat.getColor(requireContext(),it.color.rgb))
            }
        }
        binding.ivBack.setOnClickListener {
            navController.popBackStack()
        }

        binding.btnEdit.setOnClickListener {
            val action = ShowTaskFragmentDirections.actionShowTaskFragmentToUpdateTaskFragment(navArgs.taskId)
            navController.navigate(action)
        }
    }


}