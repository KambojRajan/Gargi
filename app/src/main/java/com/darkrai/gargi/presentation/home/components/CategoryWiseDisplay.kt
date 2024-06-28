package com.darkrai.gargi.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.darkrai.gargi.R
import com.darkrai.gargi.presentation.home.stateActions.HomeScreenActions
import com.darkrai.gargi.presentation.home.stateActions.HomeScreenStates
import com.darkrai.gargi.presentation.plantDescription.stateActions.PlantDescriptionActions

data class Category(
    val title:String,
    val imageId:Int
)

@Composable
fun CategoryDisplay(homeScreenStates: HomeScreenStates,homeScreenAction: (HomeScreenActions)->Unit,navHostController: NavHostController) {
    val categories = listOf(
        Category("Succulent", R.drawable.succulent),
        Category("Summer bone", R.drawable.summer),
        Category("Flowers", R.drawable.flowers),
        Category("Indoor", R.drawable.indoor),
        Category("Citrus", R.drawable.citrus),
        Category("Vines", R.drawable.vine)
    )
    LazyColumn(
        modifier = Modifier.padding(5.dp)
    ) {
        item {
            Text(
                text = "Categories",
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(5.dp))
            Box(modifier = Modifier.height(200.dp)) {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(2),
                    modifier = Modifier.padding(5.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { cat ->
                        CategoryCard(title = cat.title, imageId = cat.imageId,homeScreenAction=homeScreenAction)
                    }
                }
            }
        }
        item {
            Box(modifier = Modifier.height(500.dp)) {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(2),
                    modifier = Modifier.padding(5.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(homeScreenStates.topPlants) { plant ->
                        PlantCard(plant = plant, navHostController = navHostController)
                    }
                }
            }
        }
    }
}
