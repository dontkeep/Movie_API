package com.nicelydone.movieapi.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nicelydone.movieapi.ui.detail.fragments.DetailsFragment
import com.nicelydone.movieapi.ui.detail.fragments.StudioListFragment

class DetailAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
   override fun getItemCount(): Int {
      return 2
   }
   var id = ""
   override fun createFragment(position: Int): Fragment {
      val fragment1 = DetailsFragment()
      val fragment2 = StudioListFragment()

      fragment1.arguments = Bundle().apply {
         putInt(DetailsFragment.ARG_SECTION_NUMBER, position + 1)
         putString(DetailsFragment.MOVIE_ID, id)
      }

      fragment2.arguments =  Bundle().apply {
         putInt(StudioListFragment.ARG_SECTION_NUMBER, position + 1)
         putString(StudioListFragment.MOVIE_ID, id)
      }

      when(position){
         0 -> return fragment1
         1 -> return fragment2
      }
      return fragment2
   }
}