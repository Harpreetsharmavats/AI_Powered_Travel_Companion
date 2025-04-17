package com.example.ai_powered_travel_companion

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ai_powered_travel_companion.Adapters.ResultAdapter
import com.example.ai_powered_travel_companion.ViewModel.ResultViewModel
import com.example.ai_powered_travel_companion.ViewModel.ResultViewModelFactory
import com.example.ai_powered_travel_companion.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityResultBinding.inflate(layoutInflater)
    }
    private lateinit var adapter: ResultAdapter
    private val viewModel: ResultViewModel by viewModels { ResultViewModelFactory(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = ResultAdapter { place ->
            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtra("name", place.name)
                putExtra("address", place.address)
                putExtra("rating", place.rating.toString())
                putExtra("photoReference", place.photoReference)
            }
            startActivity(intent)
        }
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = adapter

        val place = intent.getStringExtra("place")
        if (!place.isNullOrEmpty()) {
            viewModel.search(place)
        }
        viewModel.placeList.observe(this) {
            adapter.submitList(it)
        }

    }
}