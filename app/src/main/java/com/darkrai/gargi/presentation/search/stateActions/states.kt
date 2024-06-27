package com.darkrai.gargi.presentation.search.stateActions

import com.darkrai.gargi.data.models.Plant

data class SearchStates(
    val query:String = "",
    val searchProduct:List<Plant> = emptyList(),
    val isSearching:Boolean = false
)
