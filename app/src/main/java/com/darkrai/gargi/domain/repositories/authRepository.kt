package com.darkrai.gargi.domain.repositories

import android.content.Context
import com.darkrai.gargi.data.models.User
import io.github.jan.supabase.gotrue.user.UserInfo

interface AuthRepository{
    suspend fun signIn(pEmail:String,pPassword:String):User?
    suspend fun signUp(pEmail:String,pPassword:String,context: Context):User?
    suspend fun signInViaGoogle(context: Context): User?
    suspend fun getUserOrNull(context: Context):User?
}