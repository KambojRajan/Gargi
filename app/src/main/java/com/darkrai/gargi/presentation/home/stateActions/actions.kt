package com.darkrai.gargi.presentation.home.stateActions

import com.darkrai.gargi.presentation.home.components.Category

sealed interface HomeScreenActions{
    data object getTopPlants:HomeScreenActions
    data class setSelectedCategory(val cat:Category):HomeScreenActions
    data class setIsTryingToSearch(val state:Boolean):HomeScreenActions
    data object searchSelectedCategory:HomeScreenActions
}