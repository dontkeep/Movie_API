package com.nicelydone.movieapi.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieID(
   val id: String
): Parcelable
