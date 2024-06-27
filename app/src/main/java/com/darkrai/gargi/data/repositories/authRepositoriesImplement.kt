package com.darkrai.gargi.data.repositories

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.darkrai.gargi.data.models.User
import com.darkrai.gargi.data.models.UserDto
import com.darkrai.gargi.data.models.toUser
import com.darkrai.gargi.data.utlis.SecureStorageManager
import com.darkrai.gargi.domain.repositories.AuthRepository
import com.darkrai.gargi.google_client_id_web
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.providers.builtin.IDToken
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.util.UUID
import java.util.concurrent.CancellationException
import javax.inject.Inject

class AuthRepositoryImplement @Inject constructor(
    private val auth:Auth,
    private val postgrest: Postgrest,
) : AuthRepository {

    companion object {
        const val TAG = "AuthRepository"
    }

    override suspend fun signIn(pEmail: String, pPassword: String): User? {
        return try {
            auth.signInWith(Email) {
                email = pEmail
                password = pPassword
            }
            postgrest.from("users").select {
                filter {
                    eq("email",pEmail)
                }
            }.decodeSingleOrNull<UserDto>()?.toUser()
        }catch (e:Exception){
            Log.e(TAG, e.toString())
            if (e is CancellationException){
                throw e
            }
            null
        }
    }

    override suspend fun signUp(pEmail: String, pPassword: String,context: Context): User? {
        val secureStorageManager = SecureStorageManager(context)
        return try {
            auth.signUpWith(Email) {
                email = pEmail
                password = pPassword
            }
            val userDto = UserDto(
                username = pEmail.split("@")[0],
                password = secureStorageManager.encryptData(pPassword),
                email = pEmail,
                balance = 0f,
                isAdmin = false,
                userId = auth.retrieveUserForCurrentSession().id,
                profileImg = auth.toString()
            )
            postgrest.from("users").insert(userDto)
            userDto.toUser()
        }catch (e:Exception){
            Log.e(TAG, e.toString())
            if (e is CancellationException){
                throw e
            }
            null
        }
    }

    override suspend fun signInViaGoogle(context: Context): User? {
        val secureStorageManager = SecureStorageManager(context)
        val rawNonce = UUID.randomUUID().toString()

        val credentialManager = CredentialManager.create(context)
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(google_client_id_web)
            .setNonce(hashedNonce)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        var usr: User? = null
        withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Starting Google sign-in process")
                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )
                Log.d(TAG, "Credential retrieved successfully")

                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(result.credential.data)
                val googleIdToken = googleIdTokenCredential.idToken
                Log.d(TAG, "Google ID Token retrieved: $googleIdToken")

                auth.signInWith(IDToken) {
                    idToken = googleIdToken
                    provider = Google
                    nonce = rawNonce
                }
                Log.d(TAG, "Firebase sign-in initiated")

                val user = auth.currentUserOrNull()
                Log.d(TAG, "Current user after sign-in: ${user?.id}")

                val existingUser = postgrest.from("users").select {
                    filter {
                        eq("user_id", user?.id ?: "")
                    }
                }.decodeSingleOrNull<UserDto>()

                if (existingUser == null) {
                    Log.d(TAG, "User not found in the database, creating a new user")
                    val metadata = user?.userMetadata
                    val gson = Gson()
                    val json = metadata.toString()
                    val jsonObject = gson.fromJson(json, JsonObject::class.java)
                    val avatarUrl = jsonObject.get("avatar_url").asString
                    val userDto = UserDto(
                        username = (user?.email ?: "").split("@")[0],
                        password = "",
                        email = user?.email ?: "",
                        balance = 0f,
                        isAdmin = false,
                        userId = user?.id ?: "",
                        profileImg = avatarUrl
                    )
                    usr = userDto.toUser()
                    postgrest.from("users").insert(userDto)
                    Log.d(TAG, "New user inserted into the database: ${usr?.userId}")
                } else {
                    usr = existingUser.toUser()
                    Log.d(TAG, "Existing user found: ${usr?.userId}")
                }

                val currentTime = System.currentTimeMillis()
                secureStorageManager.storeData("access_token", googleIdToken)
                secureStorageManager.storeData("exp_time", (currentTime + 3600000).toString())
                secureStorageManager.storeData("nonce", rawNonce)
                Log.d(TAG, "User session data stored securely")

            } catch (e: GoogleIdTokenParsingException) {
                Log.e(TAG, "GoogleIdTokenParsingException: ${e.localizedMessage}", e)
            } catch (e: RestException) {
                Log.e(TAG, "RestException: ${e.localizedMessage}", e)
            } catch (e: Exception) {
                if (e is CancellationException) {
                    throw e
                }
                Log.e(TAG, "Exception: ${e.localizedMessage}", e)
            }
        }
        if (usr == null) {
            Log.d(TAG, "User is null after sign-in process")
        } else {
            Log.d(TAG, "User sign-in successful: ${usr?.userId}")
        }
        return usr
    }

    override suspend fun getUserOrNull(context: Context): User? = withContext(Dispatchers.IO) {
        try {
            Log.i(TAG,"entring the process of geting user")
            val secureStorageManager = SecureStorageManager(context)
            val token = secureStorageManager.retrieveData("access_token")
            val savedNonce = secureStorageManager.retrieveData("nonce")

            if (token == null || savedNonce == null) {
                return@withContext null
            }
            Log.i(TAG,"got token")
            auth.signInWith(IDToken) {
                idToken = token
                provider = Google
                nonce = savedNonce
            }
            Log.i(TAG,"done signin")
            val user = auth.currentUserOrNull()
            Log.d(TAG, "Current user after sign-in: ${user?.id}")

            val existingUser = postgrest.from("users").select {
                filter {
                    eq("user_id", user?.id ?: "")
                }
            }.decodeSingleOrNull<UserDto>()
            return@withContext existingUser?.toUser()
        } catch (e: GoogleIdTokenParsingException) {
            Log.e(TAG, "GoogleIdTokenParsingException: ${e.localizedMessage}", e)
        } catch (e: RestException) {
            Log.e(TAG, "RestException: ${e.localizedMessage}", e)
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            Log.e(TAG, "Exception: ${e.localizedMessage}", e)
        }
        return@withContext null
    }
}