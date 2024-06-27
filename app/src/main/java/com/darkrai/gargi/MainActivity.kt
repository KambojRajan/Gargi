 package com.darkrai.gargi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.darkrai.gargi.presentation.auth.signIn.stateActions.SignInActions
import com.darkrai.gargi.presentation.auth.signIn.stateActions.SignInViewModel
import com.darkrai.gargi.presentation.navigation.components.Navigation
import com.darkrai.gargi.ui.theme.GargiTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers

 @AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GargiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navHostController = rememberNavController()
                    var isInitialized by rememberSaveable {
                        mutableStateOf(false)
                    }
                    if(isInitialized){
                        Navigation(
                            navHostController = navHostController
                        )
                    }else{
                        val signInViewModel = hiltViewModel<SignInViewModel>()
                        val context = LocalContext.current
                        val coroutineScope = rememberCoroutineScope()
                        LaunchedEffect(Unit) {
                            signInViewModel.onAction(SignInActions.GetUserOrNull(context))
                            Log.i("main activity","helo")
                            isInitialized = true
                        }
                    }
                }
            }
        }
    }
}
