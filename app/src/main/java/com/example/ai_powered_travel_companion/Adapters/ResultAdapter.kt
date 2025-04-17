package com.example.ai_powered_travel_companion.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ai_powered_travel_companion.Modals.PlacesItems
import com.example.ai_powered_travel_companion.databinding.PlacesBinding

class ResultAdapter(private val onClick : (PlacesItems) -> Unit) : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {
    private var places = listOf<PlacesItems>()
    inner class ResultViewHolder(private val binding: PlacesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(placesItems: PlacesItems) {
            binding.name.text = placesItems.name
            binding.ratings.text = "Ratings ${placesItems.rating}"
            placesItems.photoReference?.let {
                val photoUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=$it&key=AIzaSyBhasG_ac-KDpvSUGRwHpNpB-6asubmuKg"
                Glide.with(binding.root.context).load(photoUrl).into(binding.imageView)
            }
            binding.root.setOnClickListener { onClick(placesItems) }
        }

    }
    fun submitList(list: List<PlacesItems>) {
        places = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(PlacesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = places.size

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(places[position])
    }


}