package com.darkrai.gargi.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.darkrai.gargi.R
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
    val categories = listOf(
        Category("Succulent", R.drawable.succulent),
        Category("Summer bone", R.drawable.summer),
        Category("Flowers", R.drawable.flowers),
        Category("Indoor", R.drawable.indoor),
        Category("Citrus", R.drawable.citrus),
        Category("Vines", R.drawable.vine)
    )
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
                         Divider(title = "Categories")
                         LazyRow(
                             modifier = Modifier.fillMaxWidth().height(100.dp),
                             horizontalArrangement = Arrangement.spacedBy(8.dp)
                         ) {
                             items(categories) { cat ->
                                 CategoryCard(title = cat.title, imageId = cat.imageId, homeScreenAction = homeScreenAction)
                             }
                         }
                     }
                 }
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(10.dp)
        ) {
            CategoryDisplay(homeScreenStates = homeScreenStates, homeScreenAction = homeScreenAction,navHostController)
        }
    }

}