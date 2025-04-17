package com.example.ai_powered_travel_companion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ai_powered_travel_companion.databinding.ActivityDetailsBinding


class DetailsActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityDetailsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}