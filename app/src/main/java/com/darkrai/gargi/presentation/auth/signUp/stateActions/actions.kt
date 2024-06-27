package com.darkrai.gargi.presentation.auth.signUp.stateActions

import android.content.Context

sealed interface SignUpActions {
    data class SignUp(val context:Context): SignUpActions
    data class SetEmail(val email:String): SignUpActions
    data class SetPassword(val password: String): SignUpActions
}