package com.example.ai_powered_travel_companion

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ai_powered_travel_companion.Adapters.ResultAdapter
import com.example.ai_powered_travel_companion.ViewModel.ResultViewModel
import com.example.ai_powered_travel_companion.ViewModel.ResultViewModelFactory
import com.example.ai_powered_travel_companion.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private val binding by lazy { ActivityResultBinding.inflate(layoutInflater) }
    private lateinit var adapter: ResultAdapter
    private val viewModel: ResultViewModel by viewModels { ResultViewModelFactory(this) }

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

        if (fineLocationGranted || coarseLocationGranted) {
            setupRecyclerViewAndLoadData()
        } else {
            Toast.makeText(this, "Location permission is required!", Toast.LENGTH_SHORT).show()
            finish() // Close this screen if permission denied
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        checkAndRequestLocationPermission()
    }

    private fun checkAndRequestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            setupRecyclerViewAndLoadData()
        } else {

            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun setupRecyclerViewAndLoadData() {
        adapter = ResultAdapter { place ->
            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtra("name", place.name)
                putExtra("address", place.address)
                putExtra("rating", place.rating.toString())
                putExtra("photoReference", place.photoReference)
                putExtra("opening", place.openingHours)
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
