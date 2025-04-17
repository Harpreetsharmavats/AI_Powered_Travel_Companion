package com.example.ai_powered_travel_companion.Modals

import com.google.android.libraries.places.api.model.OpeningHours

data class PlacesItems(
    val name: String,
    val address: String,
    val rating: Double?,
    val photoReference: String?,
    val openingHours: String
)
