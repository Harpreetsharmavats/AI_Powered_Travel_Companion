package com.example.ai_powered_travel_companion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ai_powered_travel_companion.databinding.ActivityDetailsBinding


class DetailsActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityDetailsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val name = intent.getStringExtra("name")
        val address = intent.getStringExtra("address")
        val rating = intent.getStringExtra("rating")
        val photoReference = intent.getStringExtra("photoReference")
        binding.place.text = name
        binding.address.text = address
        binding.openingHours.text = "Rating: $rating"

        if (!photoReference.isNullOrEmpty()) {
            val photoUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=$photoReference&key=YOUR_API_KEY"
            Glide.with(this).load(photoUrl).into(binding.placeimage)
        }

    }
}