package com.darkrai.gargi.data.utlis

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import android.util.Base64
import android.util.Log
import java.security.KeyStore

class SecureStorageManager(context: Context) {

    private val sharedPreferences = EncryptedSharedPreferences.create(
        "secure_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        private const val KEY_ALIAS = "my_secret_key"
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val IV_SIZE = 12
    }

    init {
        generateKeyIfNeeded()
    }

    private fun generateKeyIfNeeded() {
        try {
            val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
            keyStore.load(null)
            if (!keyStore.containsAlias(KEY_ALIAS)) {
                val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
                val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build()
                keyGenerator.init(keyGenParameterSpec)
                keyGenerator.generateKey()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Key generator", e.toString())
        }
    }

    private fun getSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)
        return keyStore.getKey(KEY_ALIAS, null) as SecretKey
    }

    fun encryptData(data: String): String {
        return try {
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
            val iv = cipher.iv
            val encryptedData = cipher.doFinal(data.toByteArray(StandardCharsets.UTF_8))
            Base64.encodeToString(iv + encryptedData, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("data encrypt", e.toString())
            ""
        }
    }

    fun decryptData(encryptedData: String): String {
        return try {
            val decodedData = Base64.decode(encryptedData, Base64.DEFAULT)
            val iv = decodedData.copyOfRange(0, IV_SIZE)
            val encryptedBytes = decodedData.copyOfRange(IV_SIZE, decodedData.size)

            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), GCMParameterSpec(128, iv))
            val decryptedData = cipher.doFinal(encryptedBytes)
            String(decryptedData, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("data decrypt", e.toString())
            ""
        }
    }

    fun storeData(key: String, data: String) {
        val encryptedData = encryptData(data)
        sharedPreferences.edit().putString(key, encryptedData).apply()
    }

    fun retrieveData(key: String): String? {
        val encryptedData = sharedPreferences.getString(key, null)
        return encryptedData?.let { decryptData(it) }
    }
}
