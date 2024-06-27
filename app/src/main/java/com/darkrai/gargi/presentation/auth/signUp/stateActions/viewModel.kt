package com.darkrai.gargi.presentation.auth.signUp.stateActions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkrai.gargi.data.repositories.AuthRepositoryImplement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepo:AuthRepositoryImplement
):ViewModel() {
    private val _states = MutableStateFlow(SignUpStates())
    val states = _states.asStateFlow()

    companion object{
        val TAG = "SignUpViewModel"
    }

    fun onAction(action: SignUpActions) {
        when(action){
            is SignUpActions.SetEmail -> {
                _states.update {
                    it.copy(
                        email = it.email
                    )
                }
            }
            is SignUpActions.SetPassword -> {
                _states.update {
                    it.copy(
                        password = it.password
                    )
                }
            }
            is SignUpActions.SignUp -> {
                try {
                    _states.update {
                        it.copy(
                            state = States.PENDING
                        )
                    }
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

                    if(_states.value.errorMessage != null || _states.value.email == null || _states.value.password == null){
                        return
                    }
                    val context = action.context
                    viewModelScope.launch {
                        authRepo.signUp(
                            pEmail = _states.value.email.toString(),
                            pPassword = _states.value.password.toString(),
                            context = context
                        )
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
                        throw  e
                    }
                    Log.e(TAG, e.toString())
                }
            }
        }
    }

    private fun validateEmail(email:String?):String?{
        if(email == null){
            _states.update {
                it.copy(
                    state = States.ERROR
                )
            }
            return "Please provide an email."
        }
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex()
        if(email.matches(emailRegex)){
            _states.update {
                it.copy(
                    state = States.ERROR
                )
            }
            return "Please provide a valid email."
        }
        _states.update {
            it.copy(
                state = States.SUCCESS
            )
        }
        return null
    }

    private fun validPassword(password: String?):String?  {
        if(password == null){
            _states.update {
                it.copy(
                    state = States.ERROR
                )
            }
            return "Please provide an password."
        }
        val passwordRegex = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?])[A-Za-z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]{4,}$".toRegex()
        if(password.matches(passwordRegex)){
            _states.update {
                it.copy(
                    state = States.ERROR
                )
            }
            return "Please provide a valid password."
        }
        _states.update {
            it.copy(
                state = States.SUCCESS
            )
        }
        return null
    }
}