package com.example.ai_powered_travel_companion

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ai_powered_travel_companion.databinding.ActivityMainBinding
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var placeClient: PlacesClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, getString(R.string.apiKey))
        }
        placeClient = Places.createClient(this)


        binding.place.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (query.isNotBlank()) {
                    searchPlaces(query)
                }
            }
        })


        binding.findplace.setOnClickListener {
            val selectedPlace = binding.place.text.toString().trim()
            if (selectedPlace.isBlank()) {
                Toast.makeText(this, "Please enter a place", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("place", selectedPlace)
                startActivity(intent)
            }
        }
    }

    private fun searchPlaces(query: String) {
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .build()

        placeClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                val suggestions = response.autocompletePredictions.map { it.getFullText(null).toString() }
                val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, suggestions)
                binding.place.setAdapter(adapter)
                binding.place.showDropDown()
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}
