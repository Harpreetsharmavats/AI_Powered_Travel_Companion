package com.example.ai_powered_travel_companion.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ai_powered_travel_companion.Modals.PlacesItems
import com.example.ai_powered_travel_companion.Repository.PlaceRepository

class ResultViewModel(private val repository: PlaceRepository) : ViewModel() {

    private val _placeList = MutableLiveData<List<PlacesItems>>()
    val placeList : LiveData<List<PlacesItems>> get() = _placeList

    fun search(place : String){
        repository.searchPlaces(place){result->
            _placeList.postValue(result)
        }
    }
}