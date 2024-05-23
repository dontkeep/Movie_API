package com.nicelydone.movieapi.ui.detail.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.nicelydone.movieapi.BuildConfig
import com.nicelydone.movieapi.R
import com.nicelydone.movieapi.databinding.FragmentDetailsBinding
import com.nicelydone.movieapi.viewmodel.DetailViewModel

class DetailsFragment : Fragment() {

   private lateinit var binding: FragmentDetailsBinding
   val apiKey = BuildConfig.API_KEY

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? { // Inflate the layout for this fragment
      return inflater.inflate(R.layout.fragment_details, container, false)
   }

   @SuppressLint("SetTextI18n")
   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding = FragmentDetailsBinding.bind(view)

      val id = arguments?.getString(MOVIE_ID)
      val position = arguments?.getInt(ARG_SECTION_NUMBER)

      val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]

      if (position == 1){
         if (id != null) {
            detailViewModel.getDetail(id = id.toString(), api_key = apiKey)
         }
      }

      detailViewModel.detailResult.observe(viewLifecycleOwner){item ->
         binding.titleDetail.text = "Title : " + item.title
         binding.budgetDetail.text = "Budget : USD" + item.budget.toString()
         binding.revenueDetail.text = "Revenue : USD" + item.revenue.toString()
         binding.releaseDateDetail.text = "Release Date : " + item.releaseDate
         binding.averageRatingDetail.text = "Average Rating : " + item.voteAverage
         binding.votecountDetail.text = "Total Rating : " + item.voteCount
         binding.runtimeMinuteDetail.text = "Runtime : " + item.runtime + " Minutes"
         binding.overviewDetail.text = "Overview : " + item.overview
      }
      detailViewModel.loading.observe(viewLifecycleOwner){loading ->
         if (loading) {
            binding.progressBar.visibility = View.VISIBLE
         } else {
            binding.progressBar.visibility = View.INVISIBLE
         }
      }
   }
   companion object {
      const val ARG_SECTION_NUMBER = "section_number"
      const val MOVIE_ID = "movie_id"
   }
}