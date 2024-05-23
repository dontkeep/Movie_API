package com.nicelydone.movieapi.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nicelydone.movieapi.BuildConfig
import com.nicelydone.movieapi.databinding.MovieItemBinding
import com.nicelydone.movieapi.models.MovieID
import com.nicelydone.movieapi.models.networks.response.ResultsItem3
import com.nicelydone.movieapi.ui.detail.DetailActivity

class MainAdapter: ListAdapter<ResultsItem3, MainAdapter.ViewHolder>(DIFF_CALLBACK) {
   class ViewHolder(private val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root) {
      private val base_image_url = BuildConfig.IMAGE_URL
      fun bind(item: ResultsItem3){
         binding.title.text = item.title
         binding.popularity.text = item.voteAverage.toString()
         Glide.with(binding.image.context).load(base_image_url + item.posterPath).into(binding.image)
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val item = getItem(position)
      holder.bind(item)

      val movieID = MovieID(
         item.id.toString()
      )
      holder.itemView.setOnClickListener { view ->
         val intent = Intent(holder.itemView.context, DetailActivity::class.java)
         intent.putExtra(DetailActivity.MOVIE_ID, movieID)
         view.context.startActivity(intent)
      }
   }

   companion object {
      val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResultsItem3>(){
         override fun areItemsTheSame(oldItem: ResultsItem3, newItem: ResultsItem3): Boolean {
            return oldItem == newItem
         }

         override fun areContentsTheSame(oldItem: ResultsItem3, newItem: ResultsItem3): Boolean {
            return oldItem == newItem
         }
      }
   }
}