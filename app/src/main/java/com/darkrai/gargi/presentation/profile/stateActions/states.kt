package com.darkrai.gargi.presentation.profile.stateActions

import com.darkrai.gargi.data.models.OrderDto

data class ProfileStates(
    val pendingOrders:List<OrderDto> = emptyList(),
    val totalOrders:Int = 0,
    val totalDonatedPlants:Int = 0
)
