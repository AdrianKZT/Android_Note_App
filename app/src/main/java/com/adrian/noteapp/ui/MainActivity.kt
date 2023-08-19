package com.adrian.noteapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adrian.noteapp.R
import com.adrian.noteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}