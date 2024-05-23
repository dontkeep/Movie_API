package com.nicelydone.movieapi.ui.detail.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.nicelydone.movieapi.BuildConfig
import com.nicelydone.movieapi.R
import com.nicelydone.movieapi.databinding.FragmentStudioListBinding
import com.nicelydone.movieapi.viewmodel.DetailViewModel

class StudioListFragment : Fragment() {

   private lateinit var binding: FragmentStudioListBinding
   val api_key = BuildConfig.API_KEY

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      return inflater.inflate(R.layout.fragment_studio_list, container, false)
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding = FragmentStudioListBinding.bind(view)

      val id = arguments?.getString(MOVIE_ID)
      val position = arguments?.getInt(ARG_SECTION_NUMBER)


      val layoutManager =  LinearLayoutManager(activity)
      binding.rvStudios.layoutManager = layoutManager

      val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
      binding.rvStudios.addItemDecoration(itemDecoration)

      val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]

      if (position == 2){
         if (id != null) {
            detailViewModel.getDetail(id, api_key)
         }
      }

      detailViewModel.detailResult.observe(viewLifecycleOwner){item ->
         val adapter = StudioAdapter()
         adapter.submitList(item.productionCompanies)
         binding.rvStudios.adapter = adapter
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