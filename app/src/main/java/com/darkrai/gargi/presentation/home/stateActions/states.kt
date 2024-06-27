package com.darkrai.gargi.presentation.home.stateActions

import com.darkrai.gargi.data.models.Plant
import com.darkrai.gargi.presentation.home.components.Category

data class HomeScreenStates(
    val topPlants:List<Plant> = emptyList(),
    val selectedCategory: Category? = null,
    val isTryingToSearch:Boolean = false
)
