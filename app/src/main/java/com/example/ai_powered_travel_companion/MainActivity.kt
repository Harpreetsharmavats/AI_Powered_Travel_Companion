package com.example.ai_powered_travel_companion

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ai_powered_travel_companion.databinding.ActivityMainBinding
import com.google.android.libraries.places.api.Places


class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if(!Places.isInitialized()){
            Places.initialize(applicationContext, R.string.apiKey.toString())
        }
        binding.findplace.setOnClickListener {
            val place = binding.place.editText?.text.toString().trim()
            if (place.isBlank()) {
                Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show()
            } else {
                findPlace(place)
            }
        }
    }

    private fun findPlace(place: String) {

        val intent = Intent(this,ResultActivity::class.java)
        intent.putExtra("place",place)
        startActivity(intent)
    }
}