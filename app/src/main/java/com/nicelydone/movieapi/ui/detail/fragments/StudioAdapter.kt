package com.nicelydone.movieapi.ui.detail.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicelydone.movieapi.databinding.StudioItemBinding
import com.nicelydone.movieapi.models.networks.response.ProductionCompaniesItem

class StudioAdapter: ListAdapter<ProductionCompaniesItem, StudioAdapter.ViewHolder>(DIFF_CALLBACK) {
   class ViewHolder(private val binding: StudioItemBinding): RecyclerView.ViewHolder(binding.root) {
      fun bind(item: ProductionCompaniesItem){
         binding.studioName.text = item.name
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val binding =  StudioItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val item = getItem(position)
      holder.bind(item)
   }

   companion object {
      val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductionCompaniesItem>(){
         override fun areItemsTheSame(oldItem: ProductionCompaniesItem, newItem: ProductionCompaniesItem): Boolean {
            return oldItem == newItem
         }

         override fun areContentsTheSame(oldItem: ProductionCompaniesItem, newItem: ProductionCompaniesItem): Boolean {
            return oldItem == newItem
         }
      }
   }
}