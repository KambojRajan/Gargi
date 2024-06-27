package com.darkrai.gargi.presentation.auth.signIn.stateActions

import com.darkrai.gargi.data.models.User
import com.darkrai.gargi.presentation.auth.signUp.stateActions.States

data class SignInStates(
    val password: String?="",
    val email: String?="",
    val user: User?=null,
    val errorMessage:String?="",
    val state: States? = States.NONE
)
