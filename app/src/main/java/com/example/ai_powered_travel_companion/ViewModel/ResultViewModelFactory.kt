package com.example.ai_powered_travel_companion.ViewModel


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ai_powered_travel_companion.Repository.PlaceRepository

class ResultViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ResultViewModel(PlaceRepository(context)) as T
    }

}