plugins {
   alias(libs.plugins.androidApplication)
   alias(libs.plugins.jetbrainsKotlinAndroid)
   id("kotlin-parcelize")
   id("kotlin-kapt")
}

android {
   namespace = "com.nicelydone.movieapi"
   compileSdk = 34

   defaultConfig {
      applicationId = "com.nicelydone.movieapi"
      minSdk = 26
      targetSdk = 34
      versionCode = 1
      versionName = "1.0"

      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
   }

   buildFeatures {
      viewBinding = true
      buildConfig = true
   }

   buildTypes {
      release {
         isMinifyEnabled = false
         proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
         )
         buildConfigField("String", "API_KEY", "\"fa2a4ff369a28e189ca4f1af1d477a35\"")
         buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
         buildConfigField("String", "IMAGE_URL", "\"https://image.tmdb.org/t/p/original\"")
      }
      debug {
         buildConfigField("String", "API_KEY", "\"fa2a4ff369a28e189ca4f1af1d477a35\"")
         buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
         buildConfigField("String", "IMAGE_URL", "\"https://image.tmdb.org/t/p/original\"")
      }
   }
   compileOptions {
      sourceCompatibility = JavaVersion.VERSION_1_8
      targetCompatibility = JavaVersion.VERSION_1_8
   }
   kotlinOptions {
      jvmTarget = "1.8"
   }
}

dependencies {

   implementation(libs.androidx.core.ktx)
   implementation(libs.androidx.appcompat)
   implementation(libs.material)
   implementation(libs.androidx.activity)
   implementation(libs.androidx.constraintlayout)
   testImplementation(libs.junit)
   androidTestImplementation(libs.androidx.junit)
   androidTestImplementation(libs.androidx.espresso.core)
   implementation(libs.retrofit)
   implementation(libs.converter.gson)
   implementation(libs.logging.interceptor)
   implementation(libs.glide)
   implementation(libs.core)
}