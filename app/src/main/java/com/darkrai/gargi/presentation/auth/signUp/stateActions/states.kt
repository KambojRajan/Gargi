package com.darkrai.gargi.presentation.auth.signUp.stateActions

enum class States {
    PENDING,
    ERROR,
    SUCCESS,
    NONE
}

data class SignUpStates(
    val email: String? = "",
    val password: String? = "",
    val state: States? = States.NONE,
    val errorMessage:String?=null
)
