package com.darkrai.gargi.presentation.search.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.darkrai.gargi.R
import com.darkrai.gargi.data.models.Plant
import com.darkrai.gargi.presentation.navigation.components.Routes
import com.darkrai.gargi.presentation.search.stateActions.SearchStates

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchResult(searchStates:SearchStates,navHostController: NavHostController) {
    LazyColumn(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
        items(searchStates.searchProduct){plant->
            CompactPlantCard(plant = plant) {
                navHostController.navigate("plants/${plant.id}")
            }
        }
    }
}


@Composable
fun CompactPlantCard(plant:Plant,onClick:()->Unit) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = plant.images[0],
            contentDescription = plant.name,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.4f)
                .clip(RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = plant.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color= MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = plant.price.toString(),
                    fontWeight = FontWeight.Bold,
                    color= MaterialTheme.colorScheme.secondary
                )
            }
            Text(
                text =plant.description,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}