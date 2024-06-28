package com.darkrai.gargi.presentation.navigation.components


import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.darkrai.gargi.presentation.auth.signIn.components.Error
import com.darkrai.gargi.presentation.auth.signIn.components.Loading
import com.darkrai.gargi.presentation.auth.signIn.components.SignInScreen
import com.darkrai.gargi.presentation.auth.signIn.stateActions.SignInViewModel
import com.darkrai.gargi.presentation.auth.signUp.components.SignUpScreen
import com.darkrai.gargi.presentation.auth.signUp.stateActions.SignUpViewModel
import com.darkrai.gargi.presentation.dashboard.components.DashboardScreen
import com.darkrai.gargi.presentation.dashboard.stateActions.DashboardViewModel
import com.darkrai.gargi.presentation.donate.components.DonatePlantScreen
import com.darkrai.gargi.presentation.donate.stateActions.DonateViewModel
import com.darkrai.gargi.presentation.home.components.HomeScreen
import com.darkrai.gargi.presentation.home.stateActions.HomeScreenViewModel
import com.darkrai.gargi.presentation.plantDescription.components.PlantDescriptionScreen
import com.darkrai.gargi.presentation.plantDescription.stateActions.PlantDescriptionViewModel
import com.darkrai.gargi.presentation.search.stateActions.SearchViewModel
import com.darkrai.gargi.presentation.welcome.components.WelcomeScreen

@Composable
fun Navigation(navHostController: NavHostController) {
    val signInViewModel = hiltViewModel<SignInViewModel>()
    val signInStates = signInViewModel.states.collectAsState().value

    Scaffold(
        bottomBar = {
            val navBackStackEntry = navHostController.currentBackStackEntryAsState().value
            val currentRoute = navBackStackEntry?.destination?.route
            if (signInStates.user != null && currentRoute !in arrayOf(Routes.Welcome.name,Routes.SignIn.name,Routes.SignUp.name)) {
                BottomBar(navHostController = navHostController, user = signInStates.user)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = Routes.Welcome.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.SignIn.name) {
                SignInScreen(navController = navHostController, states = signInStates, onAction = signInViewModel::onAction)
            }
            composable(Routes.Home.name) {
                val searchViewModel = hiltViewModel<SearchViewModel>()
                val searchStates = searchViewModel.states.collectAsState().value
                val homeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
                val homeScreenStates =  homeScreenViewModel.states.collectAsState().value

                HomeScreen(
                    user = signInStates.user,
                    navHostController = navHostController,
                    searchStates = searchStates,
                    searchAction = searchViewModel::onAction,
                    homeScreenStates=homeScreenStates,
                    homeScreenAction = homeScreenViewModel::onAction
                )
            }
            composable(Routes.Profile.name) {
                Text(text = "Profile")
            }
            composable(Routes.Welcome.name) {
                WelcomeScreen(
                    navHostController = navHostController,
                    onAction = signInViewModel::onAction,
                    states = signInStates
                )
            }
            composable(Routes.Dashboard.name) {
                val dashBoardViewModel = hiltViewModel<DashboardViewModel>()
                val dashboardStates =  dashBoardViewModel.states.collectAsState().value
               DashboardScreen(
                   signInStates = signInStates,
                   dashboardStates = dashboardStates,
                   dashboardActions = dashBoardViewModel::onAction
               )
            }
            composable(Routes.SignUp.name) {
                val signUpViewModel = hiltViewModel<SignUpViewModel>()
                val signUpStates =  signUpViewModel.states.collectAsState().value
                SignUpScreen(
                    navController = navHostController,
                    onAction = { action -> signUpViewModel.onAction(action) },
                    states = signUpStates
                )
            }
            composable(Routes.Loading.name) {
                Loading()
            }
            composable(Routes.Error.name) {
                Error(error = signInStates.errorMessage ?: "Internal Server Error!")
            }
            composable(Routes.Donate.name) {
                val donateViewModel = hiltViewModel<DonateViewModel>()
                val donateStates =  donateViewModel.states.collectAsState().value
                if(signInStates.user?.userId == null || signInStates.user == null){
                    navHostController.navigate(Routes.SignIn.name)
                    return@composable
                }
                DonatePlantScreen(states = donateStates, onAction = donateViewModel::onAction, userId = signInStates.user.userId)
            }
            composable("plants/{plant_id}") {
                val id = it.arguments?.getString("plant_id")
                if(id==null)return@composable
                val plantDescriptionViewModel = hiltViewModel<PlantDescriptionViewModel>()
                val plantDescriptionStates =  plantDescriptionViewModel.states.collectAsState().value
                Log.i("patanathi",id)
                PlantDescriptionScreen(plantId = id.toInt(), states = plantDescriptionStates, onEvent = plantDescriptionViewModel::onAction)
            }
        }
    }
}
