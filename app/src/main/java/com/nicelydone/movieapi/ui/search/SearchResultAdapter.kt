package com.nicelydone.movieapi.ui.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nicelydone.movieapi.BuildConfig
import com.nicelydone.movieapi.databinding.MovieItemBinding
import com.nicelydone.movieapi.databinding.SearchItemBinding
import com.nicelydone.movieapi.models.MovieID
import com.nicelydone.movieapi.models.networks.response.ResultsItem2
import com.nicelydone.movieapi.ui.detail.DetailActivity
import com.nicelydone.movieapi.ui.main.MainAdapter

class SearchResultAdapter: ListAdapter<ResultsItem2, SearchResultAdapter.ViewHolder>(DIFF_CALLBACK) {
   class ViewHolder(private val binding: SearchItemBinding): RecyclerView.ViewHolder(binding.root) {
      private val image_url = BuildConfig.IMAGE_URL
      fun bind(item: ResultsItem2){
         binding.title.text = item.title
         binding.overView.text = "Overview : " + item.overview
         binding.rating.text = "Rating : " + item.voteAverage.toString()
         Glide.with(binding.image.context).load(image_url + item.posterPath).into(binding.image)
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val item = getItem(position)
      holder.bind(item)

      val movieID = MovieID(
         item.id.toString()
      )
      holder.itemView.setOnClickListener {
         val intent = Intent(holder.itemView.context, DetailActivity::class.java)
         intent.putExtra(DetailActivity.MOVIE_ID, movieID)
         it.context.startActivity(intent)
      }
   }

   companion object {
      val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResultsItem2>(){
         override fun areItemsTheSame(oldItem: ResultsItem2, newItem: ResultsItem2): Boolean {
            return oldItem == newItem
         }

         override fun areContentsTheSame(oldItem: ResultsItem2, newItem: ResultsItem2): Boolean {
            return oldItem == newItem
         }
      }
   }
}
