package com.nicelydone.movieapi.ui.detail

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nicelydone.movieapi.BuildConfig
import com.nicelydone.movieapi.databinding.ActivityDetailBinding
import com.nicelydone.movieapi.models.MovieID
import com.nicelydone.movieapi.models.networks.response.DetailResponse
import com.nicelydone.movieapi.viewmodel.DetailViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class DetailActivity : AppCompatActivity() {
   private lateinit var binding: ActivityDetailBinding
   private val api_key = BuildConfig.API_KEY

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityDetailBinding.inflate(layoutInflater)
      setContentView(binding.root)

      val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]

      val movieId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
         intent.getParcelableExtra(MOVIE_ID, MovieID::class.java)
      } else {
         intent.getParcelableExtra(MOVIE_ID)
      }

      detailViewModel.getDetail(movieId?.id.toString(), api_key)

      detailViewModel.detailResult.observe(this) { item ->
         val youtubePlayerView = binding.youtubePlayerView

         getYoutubeVideo(youtubePlayerView, item)

         val viewPager: ViewPager2 = binding.viewPager

         val sectionPagerAdapter = DetailAdapter(this)
         sectionPagerAdapter.id = movieId?.id ?: ""

         viewPager.adapter = sectionPagerAdapter
         val tabs: TabLayout = binding.tabLayout

         TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = when (position) {
               0 -> "Description"
               1 -> "Studios"
               else -> ""
            }
         }.attach()
      }


      detailViewModel.loading.observe(this){loading ->
         if (loading) {
            binding.progressBar.visibility = View.VISIBLE
         } else {
            binding.progressBar.visibility = View.INVISIBLE
         }
      }

   }

   private fun getYoutubeVideo(youtubePlayerview: YouTubePlayerView, item: DetailResponse) {

      lifecycle.addObserver(youtubePlayerview)

      youtubePlayerview.addYouTubePlayerListener( object : AbstractYouTubePlayerListener() {
         override fun onReady(youTubePlayer: YouTubePlayer) {
            youTubePlayer.loadOrCueVideo(lifecycle, item.videos?.results?.get(0)?.key!!, 0F )
         }
      })
   }

   companion object {
      val MOVIE_ID = "Movie_id"
   }
}