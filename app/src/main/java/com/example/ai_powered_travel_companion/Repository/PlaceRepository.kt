package com.example.ai_powered_travel_companion.Repository

import android.content.Context
import android.location.Geocoder
import android.util.Log
import com.example.ai_powered_travel_companion.Modals.PlacesItems
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import java.util.Locale

class PlaceRepository(context: Context) {

    private val placesClient: PlacesClient = Places.createClient(context)
    private val geocoder = Geocoder(context, Locale.getDefault())

    fun loadTopTouristPlacesByCity(cityName: String, onComplete: (List<PlacesItems>) -> Unit) {
        try {
            val addresses = geocoder.getFromLocationName(cityName, 1)
            if (addresses.isNullOrEmpty()) {
                Log.e("PlaceRepo", "City not found: $cityName")
                onComplete(emptyList())
                return
            }

            val location = addresses[0]
            val lat = location.latitude
            val lng = location.longitude

            searchNearbyTouristPlaces(cityName,lat, lng, onComplete)

        } catch (e: Exception) {
            Log.e("PlaceRepo", "Geocoding failed: ${e.message}")
            onComplete(emptyList())
        }
    }

    private fun searchNearbyTouristPlaces(cityName: String, lat: Double, lng: Double, onComplete: (List<PlacesItems>) -> Unit) {
        val bounds = RectangularBounds.newInstance(
            LatLng(lat - 0.5, lng - 0.5),
            LatLng(lat + 0.5, lng + 0.5)
        )

        val request = FindAutocompletePredictionsRequest.builder()
            .setLocationBias(bounds)
            .setQuery(cityName)
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                val predictions = response.autocompletePredictions
                if (predictions.isEmpty()) {
                    onComplete(emptyList())
                    return@addOnSuccessListener
                }

                val placesList = mutableListOf<PlacesItems>()
                var completedFetches = 0


                predictions.forEach { prediction ->
                    val placeId = prediction.placeId
                    val fields = listOf(
                        Place.Field.NAME,
                        Place.Field.ADDRESS,
                        Place.Field.RATING,
                        Place.Field.PHOTO_METADATAS,
                        Place.Field.OPENING_HOURS
                    )

                    val fetchPlaceRequest = FetchPlaceRequest.builder(placeId, fields).build()

                    placesClient.fetchPlace(fetchPlaceRequest)
                        .addOnSuccessListener { fetchPlaceResponse ->
                            val place = fetchPlaceResponse.place
                            val photoReference = place.photoMetadatas?.firstOrNull()?.let { it.zza() }

                            val item = PlacesItems(
                                name = place.name ?: "No Name",
                                address = place.address ?: "No Address",
                                rating = place.rating,
                                photoReference = photoReference,
                                openingHours = place.openingHours?.weekdayText?.joinToString(", ") ?: "No timings"
                            )

                            placesList.add(item)
                            completedFetches++

                           onComplete(placesList)
                        }
                        .addOnFailureListener {
                            Log.e("PlaceRepo", "Nearby search failed:")

                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("PlaceRepo", "Nearby search failed: ${exception.message}")
                onComplete(emptyList())
            }
    }

}

