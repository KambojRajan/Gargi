package com.darkrai.gargi.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.darkrai.gargi.data.models.User
import com.darkrai.gargi.presentation.home.stateActions.HomeScreenActions
import com.darkrai.gargi.presentation.home.stateActions.HomeScreenStates
import com.darkrai.gargi.presentation.search.components.SearchField
import com.darkrai.gargi.presentation.search.stateActions.SearchActions
import com.darkrai.gargi.presentation.search.stateActions.SearchStates

@Composable
fun HomeScreen(
    user:User?,
    navHostController: NavHostController,
    searchStates:SearchStates,
    searchAction:(SearchActions)->Unit,
    homeScreenStates: HomeScreenStates,
    homeScreenAction:(HomeScreenActions)->Unit
) {

    LaunchedEffect(Unit){
        homeScreenAction(HomeScreenActions.getTopPlants)
    }

    Scaffold(
        topBar = {
                 Column {
                     if(homeScreenStates.isTryingToSearch){
                         SearchField(
                             states = searchStates,
                             onAction = searchAction,
                             navHostController = navHostController,
                             modifier = Modifier.padding(horizontal = 10.dp),
                             homeScreenActions = homeScreenAction
                         )
                     }else{
                         Topbar(user = user, navHostController = navHostController, homeScreenActions = homeScreenAction)
                     }
                     Box(modifier = Modifier.padding(15.dp)){
                         CategoryCard(homeScreenStates = homeScreenStates)
                     }
                 }
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            CategoryDisplay(homeScreenStates = homeScreenStates,homeScreenAction = homeScreenAction)
        }
    }

}