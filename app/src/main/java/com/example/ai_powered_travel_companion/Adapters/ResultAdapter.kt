package com.example.ai_powered_travel_companion.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ai_powered_travel_companion.databinding.PlacesBinding

class ResultAdapter : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {
    inner class ResultViewHolder(private val binding: PlacesBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(PlacesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


}