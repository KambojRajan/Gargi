package com.darkrai.gargi.presentation.auth.signIn.stateActions

import android.content.Context

sealed interface SignInActions {
    class SetEmail(val email:String):SignInActions
    class SetPassword(val password:String):SignInActions
    data class GetUserOrNull(val context:Context):SignInActions
    data object SignInViaEmail:SignInActions
    class SignInViaGoogle(val context:Context):SignInActions
}