package com.darkrai.gargi.data.utils

import android.content.Context
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.darkrai.gargi.di.SupabaseModule
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TokenRefreshLifecycleObserver(
    context: Context,
    private val client: SupabaseClient = SupabaseModule.provideSupabaseClient()
) : DefaultLifecycleObserver {

    private val sharedPreferences = EncryptedSharedPreferences.create(
        "secure_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        private const val REFRESH_TIME_THRESHOLD = 600000
        private const val EXPIRATION_TIME_KEY = "exp_time"
    }

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        coroutineScope.launch {
            while (true) {
                checkAndRefreshToken()
                delay(REFRESH_TIME_THRESHOLD.toLong() / 2)
            }
        }
    }

    private suspend fun checkAndRefreshToken() {
        val expirationTime = sharedPreferences.getLong(EXPIRATION_TIME_KEY, 0L)
        val currentTime = System.currentTimeMillis()
        if (expirationTime - currentTime <= REFRESH_TIME_THRESHOLD) {
            Log.d("TokenRefresh", "Token is about to expire. Refreshing token.")
            withContext(Dispatchers.IO) {
                try {
                    var session = client.auth.currentSessionOrNull()
                    val refreshToken = session?.refreshToken
                    if(refreshToken == null){
                        TODO(/* navigate to login*/)
                        return@withContext
                    }
                    session = client.auth.refreshSession(refreshToken)
                    TODO(/*save to pref*/)
                    val newExpirationTime = System.currentTimeMillis() + 3600000
                    sharedPreferences.edit().putLong(EXPIRATION_TIME_KEY, newExpirationTime).apply()
                    Log.d("TokenRefresh", "Token refreshed successfully. New expiration time: $newExpirationTime")
                } catch (e: Exception) {
                    Log.e("TokenRefresh", "Failed to refresh token: ${e.message}", e)
                }
            }
        } else {
            Log.d("TokenRefresh", "Token is still valid.")
        }
    }
}
