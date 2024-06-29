package com.darkrai.gargi.presentation.home.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.darkrai.gargi.presentation.home.stateActions.HomeScreenActions
import com.darkrai.gargi.presentation.home.stateActions.HomeScreenStates

data class Category(
    val title:String,
    val imageId:Int
)

@Composable
fun CategoryDisplay(homeScreenStates: HomeScreenStates,homeScreenAction: (HomeScreenActions)->Unit,navHostController: NavHostController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ){
        item(span = { GridItemSpan(2) }) {  CategoryCard(homeScreenStates = homeScreenStates) }
        item(span = { GridItemSpan(2) }) { Divider(title = "") }
        items(homeScreenStates.topPlants) { plant ->
            PlantCard(plant = plant, navHostController = navHostController)
        }
    }
}
