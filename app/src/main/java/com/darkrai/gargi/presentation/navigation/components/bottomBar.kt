package com.darkrai.gargi.presentation.navigation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.darkrai.gargi.data.models.User


@Composable
fun BottomBar(navHostController: NavHostController,user:User) {
    var currentRoute by rememberSaveable {
        mutableStateOf(Routes.Home.name)
    }
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == Routes.Home.name,
            onClick = {
                 currentRoute = Routes.Home.name
                navHostController.navigate(Routes.Home.name)
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = "Home screen",
                    modifier = Modifier.size(25.dp)
                )
            },
            label = { Text(text = Routes.Home.name)}
        )
        NavigationBarItem(
            selected = currentRoute == Routes.Profile.name,
            onClick = {
                currentRoute = Routes.Profile.name
                navHostController.navigate(Routes.Profile.name)
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "profile screen",
                    modifier = Modifier.size(25.dp)
                )
            },
            label = { Text(text = Routes.Profile.name)}
        )
        if(user.isAdmin != null && user.isAdmin){
            NavigationBarItem(
                selected = currentRoute == Routes.Dashboard.name,
                onClick = {
                    currentRoute = Routes.Dashboard.name
                    navHostController.navigate(Routes.Dashboard.name)
                },
                icon = {
                    Icon(
                        imageVector = DashboardIcon(),
                        contentDescription = "profile screen",
                        modifier = Modifier.size(25.dp)
                    )
                },
                label = { Text(text = Routes.Dashboard.name)}
            )
        }
        NavigationBarItem(
            selected = currentRoute == Routes.Donate.name,
            onClick = {
                currentRoute = Routes.Donate.name
                navHostController.navigate(Routes.Donate.name)
            },
            icon = {
                Icon(
                    imageVector = DonatePlant(),
                    contentDescription = "donate plant screen",
                    modifier = Modifier.size(25.dp)
                )
            },
            label = { Text(text = Routes.Donate.name)}
        )
    }

}

@Preview
@Composable
fun BottomBarPrev() {
    val user = User(
        username = "",
        profileImg = "",
        balance = 0f,
        userId = "",
        password = "",
        isAdmin = false,
        email = ""
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {BottomBar(navHostController = rememberNavController(), user = user)}
    ) {
        Text(modifier = Modifier.padding(it),text = "Hello")
    }
}