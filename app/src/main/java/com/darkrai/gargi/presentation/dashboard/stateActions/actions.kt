package com.darkrai.gargi.presentation.dashboard.stateActions

import android.net.Uri

sealed interface DashboardActions {
    data object GetPendingOrders:DashboardActions
    data class SetPlantName(val name:String):DashboardActions
    data class SetDescription(val description:String):DashboardActions
    data class SetPrice(val price:Float):DashboardActions
    data class SetPlantImages(val images:Uri):DashboardActions
    data class SetSelectedCategory(val cat:String):DashboardActions
    data class SetQuantity(val quantity:Int):DashboardActions
    data object SetForRescue:DashboardActions
    data object AddNewPlant:DashboardActions
    data class SearchAColumn(val column:String,val query:String):DashboardActions
}