package com.darkrai.gargi.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.darkrai.gargi.presentation.auth.signIn.stateActions.SignInStates
import com.darkrai.gargi.presentation.dashboard.components.OrderCard
import com.darkrai.gargi.presentation.dashboard.components.ProfileCard
import com.darkrai.gargi.presentation.home.components.Divider
import com.darkrai.gargi.presentation.navigation.components.DonatePlant
import com.darkrai.gargi.presentation.profile.stateActions.ProfileActions
import com.darkrai.gargi.presentation.profile.stateActions.ProfileStates

@Composable
fun ProfileScreen(signInStates:SignInStates,profileStats:ProfileStates,navHostController:NavHostController,profileActions:(ProfileActions)->Unit) {
    if(signInStates.user?.userId == null)return

    LaunchedEffect(Unit){
        profileActions(ProfileActions.GetUserPendingOrders(signInStates.user.userId))
        profileActions(ProfileActions.findUseDetails(signInStates.user.userId))
    }

    Scaffold(
        topBar = {
            ProfileCard(signInStates = signInStates)
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(8.dp)
        ) {

            item {
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                        ProfileStatsItem(
                            icon = Icons.Outlined.FavoriteBorder,
                            iconDescription = "Balance",
                            title = "Balance",
                            value = "₹ ${signInStates.user.balance ?: "0.00"}"
                        )

                        ProfileStatsItem(
                            icon = Icons.Outlined.ShoppingCart,
                            iconDescription = "Orders",
                            title = "Total orders placed.",
                            value = "${profileStats.totalOrders}"
                        )

                        ProfileStatsItem(
                            icon = DonatePlant(),
                            iconDescription = "Donations",
                            title = "Total Plants donated.",
                            value = "${profileStats.totalDonatedPlants}"
                        )
                    }
                }
            }
            item {
                Divider(title = "Ordered Plants", type = MaterialTheme.typography.titleLarge)
            }
            items(profileStats.pendingOrders) { order ->
                OrderCard(order = order, navHostController = navHostController)
            }
        }
    }
}