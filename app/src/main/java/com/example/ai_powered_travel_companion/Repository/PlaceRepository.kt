package com.example.ai_powered_travel_companion.Repository

import android.content.Context
import com.example.ai_powered_travel_companion.Modals.PlacesItems
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient

class PlaceRepository(context: Context) {
    private val placeClient : PlacesClient = Places.createClient(context)

fun searchPlaces(place: String, onComplete : (List<PlacesItems>) -> Unit){
    val request = FindAutocompletePredictionsRequest.builder().setQuery(place).build()

    placeClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
        val places = mutableListOf<PlacesItems>()
        val prediction = response.autocompletePredictions

        if (prediction.isEmpty()){
            onComplete(emptyList())
            return@addOnSuccessListener
        }

        prediction.forEach { it ->
            val placeId = it.placeId
            val fields = listOf(
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.RATING,
                Place.Field.PHOTO_METADATAS,
                Place.Field.OPENING_HOURS
            )
            val fetchRequest = FetchPlaceRequest.builder(placeId,fields).build()

            placeClient.fetchPlace(fetchRequest).addOnSuccessListener { fetchResponse->
                val place = fetchResponse.place
                val photoReference = place.photoMetadatas?.firstOrNull()?.zza()

                places.add(
                    PlacesItems(
                        name = place.name,
                        address = place.address,
                        rating = place.rating,
                        photoReference = photoReference,
                        openingHours = place.openingHours.toString()
                    )

                )
                if (places.size ==prediction.size){
                    onComplete(places)
                }
            }
        }
    }
}

}