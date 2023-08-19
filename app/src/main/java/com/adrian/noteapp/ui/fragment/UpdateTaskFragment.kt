package com.adrian.noteapp.ui.fragment

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.adrian.noteapp.R
import com.adrian.noteapp.data.model.Color
import com.adrian.noteapp.databinding.FragmentUpdateTaskBinding
import com.adrian.noteapp.ui.viewModel.UpdateTaskViewModel
import com.google.android.material.snackbar.Snackbar

class UpdateTaskFragment : Fragment() {
    private lateinit var binding: FragmentUpdateTaskBinding
    private lateinit var navController: NavController
    private val navArgs: UpdateTaskFragmentArgs by navArgs()

    private val viewModel: UpdateTaskViewModel by viewModels {
        UpdateTaskViewModel.Factory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateTaskBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.init(navArgs.taskId)

        binding.ivBack.setOnClickListener {
            navController.popBackStack()
        }
        viewModel.task.observe(viewLifecycleOwner) {
            binding.run {
                etTitle.setText(it.title)
                etDesc.setText(it.desc)
            }
        }

        viewModel.finished.observe(viewLifecycleOwner) {
            if (it.first) {
                navigateBack()
                viewModel.finished.value = Pair(false, "")

                Snackbar.make(requireView(), it.second, Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                val snackbar = Snackbar.make(
                    binding.root,
                    it,
                    Snackbar.LENGTH_LONG
                )
                snackbar.setBackgroundTint(
                    ContextCompat.getColor(
                        requireContext(), R.color.red
                    )
                )

                snackbar.show()
            }
        }

        viewModel.color.observe(viewLifecycleOwner) {
            clearSelected()
            val bgDrawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                setColor(ContextCompat.getColor(requireContext(), it.rgb))
                setStroke(8, ContextCompat.getColor(requireContext(), R.color.black))
            }

            val view = when (it) {
                Color.BLUE -> binding.btnBlue
                Color.GREEN -> binding.btnGreen
                Color.YELLOW -> binding.btnYellow
                Color.RED -> binding.btnRed
                else -> binding.btnPurple
            }

            view.background = bgDrawable
        }
    }

    private fun clearSelected() {
        binding.run {
            btnGreen.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
            btnRed.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
            btnYellow.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.yellow))
            btnBlue.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
            btnPurple.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple))

        }
    }

    private fun navigateBack() {
        val bundle = Bundle()
        bundle.putBoolean("refresh", true)
        setFragmentResult("from_update_task_fragment", bundle)
        navController.popBackStack()
    }

}