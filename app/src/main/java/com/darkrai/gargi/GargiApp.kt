package com.darkrai.gargi

import android.app.Application
import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import com.darkrai.gargi.data.utils.TokenRefreshLifecycleObserver
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GargiApp : Application() {
    private lateinit var tokenRefreshObserver: TokenRefreshLifecycleObserver

    override fun onCreate() {
        super.onCreate()
        try {
//            tokenRefreshObserver = TokenRefreshLifecycleObserver(this)
//            ProcessLifecycleOwner.get().lifecycle.addObserver(tokenRefreshObserver)
            Log.i("GargiApp", "TokenRefreshLifecycleObserver added successfully")
        } catch (e: Exception) {
            Log.e("GargiApp", "Error adding TokenRefreshLifecycleObserver: ${e.message}", e)
        }
    }
}
