package com.darkrai.gargi.presentation.donate.stateActions

sealed interface DonateActions{
    data class setPlantId(val id:Int):DonateActions
    data class Donate(val userId:String):DonateActions
    data class SetError(val err:String):DonateActions
}