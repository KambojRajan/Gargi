package com.darkrai.gargi.presentation.auth.signIn.stateActions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkrai.gargi.data.repositories.AuthRepositoryImplement
import com.darkrai.gargi.presentation.auth.signUp.stateActions.States
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepo:AuthRepositoryImplement
):ViewModel() {

    companion object {
        val TAG = "SignIn viewModel"
    }

    private val _states = MutableStateFlow(SignInStates())
    val states = _states.asStateFlow()

    fun onAction(action:SignInActions){
        when(action){
            is SignInActions.GetUserOrNull -> {
                try {
                    val context = action.context
                    viewModelScope.launch {
                        val usr = authRepo.getUserOrNull(context)
                        _states.update {
                            it.copy(
                                user = usr
                            )
                        }
                    }
                }catch (e:Exception){
                    if(e is CancellationException)throw  e
                    Log.e(TAG, e.toString())
                }
            }
            is SignInActions.SetEmail -> {
                _states.update {
                    it.copy(
                        email = action.email
                    )
                }
            }
            is SignInActions.SetPassword -> {
                _states.update {
                    it.copy(
                        password = action.password
                    )
                }
            }

            SignInActions.SignInViaEmail -> {
                try {
                    _states.update {
                        it.copy(
                            state = States.PENDING
                        )
                    }
                    _states.update {
                        it.copy(
                            errorMessage = validateEmail(_states.value.email)
                        )
                    }

                    _states.update {
                        it.copy(
                            errorMessage = validPassword(_states.value.password)
                        )
                    }

                    if(_states.value.errorMessage != null){
                        return
                    }

                    viewModelScope.launch {
                        val user =  authRepo.signIn(
                            pEmail = _states.value.email.toString(),
                            pPassword = _states.value.password.toString()
                        )
                        _states.update {
                            it.copy(
                                user = user
                            )
                        }
                    }

                }catch (e:Exception){
                    _states.update {
                        it.copy(
                            state = States.ERROR
                        )
                    }
                    if(e is CancellationException){
                        throw e
                    }
                    Log.e(TAG, e.toString())
                }
            }
           is SignInActions.SignInViaGoogle -> {
               try {
                   _states.update {
                       it.copy(
                           state = States.PENDING
                       )
                   }
                   viewModelScope.launch {
                      val usr = authRepo.signInViaGoogle(action.context)
                       _states.update {
                           it.copy(
                               user = usr
                           )
                       }
                   }
                   _states.update {
                       it.copy(
                           state = States.SUCCESS
                       )
                   }
               }catch (e:Exception){
                   _states.update {
                       it.copy(
                           state = States.ERROR
                       )
                   }
                   if(e is CancellationException){
                       throw e
                   }
                   Log.e(TAG, e.toString())
               }
           }
        }
    }

    private fun validateEmail(email:String?):String?{
        if(email == null)return "Please provide an email."
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex()
        if(email.matches(emailRegex)){
            return "Please provide a valid email."
        }
        return null
    }

    fun validPassword(password: String?):String?  {
        if(password == null)return "Please provide an password."
        val passwordRegex = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?])[A-Za-z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]{4,}$".toRegex()
        if(password.matches(passwordRegex)){
            return "Please provide a valid password."
        }
        return null
    }
}