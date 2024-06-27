package com.darkrai.gargi.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.darkrai.gargi.R
import com.darkrai.gargi.data.models.Plant
import com.darkrai.gargi.data.models.User
import com.darkrai.gargi.presentation.home.stateActions.HomeScreenActions
import com.darkrai.gargi.presentation.home.stateActions.HomeScreenStates
import com.darkrai.gargi.presentation.navigation.components.Routes
import com.darkrai.gargi.ui.theme.GargiTheme

@Composable
fun Topbar(user: User?,navHostController: NavHostController,homeScreenActions: (HomeScreenActions)->Unit) {
    if(user == null)return
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .padding(horizontal = 10.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (user.profileImg != null) {
                    AsyncImage(
                        model = user.profileImg,
                        contentDescription = user.username,
                        modifier = Modifier
                            .clickable {
                                navHostController.navigate(Routes.Profile.name)
                            }
                            .size(35.dp)
                            .clip(CircleShape)
                            .border(1.dp, MaterialTheme.colorScheme.onPrimaryContainer, CircleShape)
                    )
                } else {
                    TextButton(
                        onClick = {
                            navHostController.navigate(Routes.Profile.name)
                        },
                        modifier = Modifier
                            .size(35.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondaryContainer),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = user.username[0].toString(),
                            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSecondaryContainer)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = user.username,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold
                )
            }
            IconButton(
                onClick = {
                    homeScreenActions(HomeScreenActions.setIsTryingToSearch(true))
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search"
                )
            }
        }
    }
}
@Composable
fun CategoryCard(homeScreenStates: HomeScreenStates) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 5.dp,
                    topEnd = 40.dp,
                    bottomEnd = 5.dp,
                    bottomStart = 40.dp
                )
            ),
    ) {
       Box(modifier = Modifier.fillMaxSize()){
           Image(
               painter = painterResource(id = (homeScreenStates.selectedCategory?.imageId ?: R.drawable.welcome)),
               contentDescription = null,
               modifier = Modifier
                   .fillMaxSize()
                   .clip(RoundedCornerShape(10.dp)),
               contentScale = ContentScale.Crop
           )
           Column(
               modifier = Modifier
                   .fillMaxSize()
                   .padding(16.dp),
               verticalArrangement = Arrangement.Bottom
           ) {
               Text(
                   text = "Be a proud plant parent.",
                   style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.secondary)
               )
               Spacer(modifier = Modifier.height(2.dp))
               Text(
                   text = (homeScreenStates.selectedCategory?.title?:"Top Seller").toString(),
                   style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.tertiary),
                   fontWeight = FontWeight.Bold
               )
           }
       }
    }
}


@Composable
fun PlantCard(plant: Plant) {
    ElevatedCard(
        modifier = Modifier
            .width(330.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .shadow(4.dp, RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            AsyncImage(
                model = plant.images[0],
                contentDescription = plant.name,
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
               Row(
                   horizontalArrangement = Arrangement.SpaceBetween
               ) {
                   Text(
                       text = plant.name,
                       style = MaterialTheme.typography.titleMedium,
                       fontWeight = FontWeight.Bold,
                       maxLines = 1,
                       overflow = TextOverflow.Ellipsis
                   )
                   Text(
                       text = "₹ ${plant.price}",
                       style = MaterialTheme.typography.titleSmall,
                       fontWeight = FontWeight.SemiBold,
                       color = MaterialTheme.colorScheme.secondary
                   )
               }
                Text(
                    text = plant.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
        }
    }
}

@Composable
fun CategoryCard(title: String, imageId: Int,homeScreenAction: (HomeScreenActions)->Unit) {
    Box(
        modifier = Modifier
            .width(160.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.background)
            .clickable {
                homeScreenAction(HomeScreenActions.setSelectedCategory(Category(title, imageId)))
                homeScreenAction(HomeScreenActions.searchSelectedCategory)
            },
        contentAlignment = Alignment.TopStart
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, MaterialTheme.colorScheme.tertiary),
                        startY = 50f
                    )
                )
        )
        Text(
            text = title,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}
