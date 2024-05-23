package com.nicelydone.movieapi.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nicelydone.movieapi.BuildConfig
import com.nicelydone.movieapi.databinding.ActivityMainBinding
import com.nicelydone.movieapi.ui.search.SearchResultActivity
import com.nicelydone.movieapi.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
   private lateinit var binding: ActivityMainBinding
   private val api_key = BuildConfig.API_KEY

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityMainBinding.inflate(layoutInflater)
      setContentView(binding.root)

      val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

      binding.searchView.setupWithSearchBar(binding.searchBar)
      binding.searchView.editText.setOnEditorActionListener { textView, i, keyEvent ->
         binding.searchView.hide()
         val intent = Intent(this, SearchResultActivity::class.java)
         Log.i("inside searchView", binding.searchView.text.toString())
         val bundle = Bundle()
         bundle.putString("EXTRA_SELECTED_USER", binding.searchView.text.toString())
         intent.putExtras(bundle)
         setResult(RESULT, intent)
         startActivity(intent)
         true
      }

      val gridLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
      binding.rvMovie.layoutManager = gridLayoutManager

      mainViewModel.getTrendingResult(api_key)
      mainViewModel.trendingResult.observe(this){items ->
         val adapter = MainAdapter()
         adapter.submitList(items.results)
         binding.rvMovie.adapter = adapter
      }

      mainViewModel.loading.observe(this){loading ->
         if(loading){
            binding.progressBar.visibility = View.VISIBLE
         } else {
            binding.progressBar.visibility = View.INVISIBLE
         }
      }
   }

   companion object {
      val EXTRA_SELECTED_USER = "extra_selected_user"
      val RESULT = 110
   }
}