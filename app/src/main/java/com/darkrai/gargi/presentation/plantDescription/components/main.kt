package com.darkrai.gargi.presentation.plantDescription.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darkrai.gargi.presentation.plantDescription.stateActions.PlantDescriptionActions
import com.darkrai.gargi.presentation.plantDescription.stateActions.PlantDescriptionStates
import com.darkrai.gargi.ui.theme.GargiTheme

@Composable
fun PlantDescriptionScreen(plantId:Int,states:PlantDescriptionStates,onEvent:(PlantDescriptionActions)->Unit) {

    LaunchedEffect(Unit){
        onEvent(PlantDescriptionActions.GetPlantById(plantId))
    }
    val context = LocalContext.current
    GargiTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                ) {
                    Carousel(imageList = states.selectedPlant?.images?: emptyList())
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Mangostine",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 25.sp
                            )
                            Text(
                                text = "$20",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp
                            )
                        }
                        Text(
                            text = states.selectedPlant?.description?:"",
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    Button(
                        onClick = {

                        },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(text = "Buy this plant", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

