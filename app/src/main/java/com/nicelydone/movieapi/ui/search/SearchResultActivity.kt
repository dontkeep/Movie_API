package com.nicelydone.movieapi.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nicelydone.movieapi.BuildConfig
import com.nicelydone.movieapi.R
import com.nicelydone.movieapi.databinding.ActivitySearchResultBinding
import com.nicelydone.movieapi.viewmodel.MainViewModel

class SearchResultActivity : AppCompatActivity() {
   lateinit var binding: ActivitySearchResultBinding
   private val api_key = BuildConfig.API_KEY

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivitySearchResultBinding.inflate(layoutInflater)
      setContentView(binding.root)

      val layoutManager = LinearLayoutManager(this)
      binding.rvSearchResult.layoutManager = layoutManager

      val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
      binding.rvSearchResult.addItemDecoration(itemDecoration)

      val data = getIntent().extras?.getString("EXTRA_SELECTED_USER")

      val searchQuery = data
      Log.i("inside searchQuery", searchQuery.toString())

      val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

      mainViewModel.getSearchResult(api_key, searchQuery)

      mainViewModel.searchResult.observe(this){items ->
         val adapter = SearchResultAdapter()
         adapter.submitList(items.results)
         binding.rvSearchResult.adapter = adapter
      }
      mainViewModel.loading.observe(this){loading ->
         if (loading){
            binding.progressBar.visibility = View.VISIBLE
         }else{
            binding.progressBar.visibility = View.INVISIBLE
         }
      }
   }
}