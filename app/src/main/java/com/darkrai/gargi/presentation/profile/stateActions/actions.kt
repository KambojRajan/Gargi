package com.darkrai.gargi.presentation.profile.stateActions

sealed interface ProfileActions {
    data class GetUserPendingOrders(val userId:String):ProfileActions

    data class findUseDetails(val userId:String):ProfileActions
}