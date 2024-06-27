package com.darkrai.gargi.presentation.dashboard.stateActions

import android.net.Uri
import com.darkrai.gargi.data.models.OrderDto

data class DashboardStates(
    val pendingOrders:List<OrderDto> = emptyList(),
    val plantName:String= "",
    val plantDescription:String = "",
    val plantPrice:Float = 0f,
    val forRescue:Boolean = false,
    val plantImages:MutableList<Uri> = mutableListOf(),
    val category: String = "top seller",
    val quantity:Int = 0,
    val errorMessage:String = ""
)
