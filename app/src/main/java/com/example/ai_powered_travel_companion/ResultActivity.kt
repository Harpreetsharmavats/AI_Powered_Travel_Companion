package com.example.ai_powered_travel_companion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ai_powered_travel_companion.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityResultBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}