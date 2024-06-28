package com.darkrai.gargi.presentation.plantDescription.stateActions

import com.darkrai.gargi.data.models.Plant

sealed interface PlantDescriptionActions {
    data class GetPlantById(val plantId:Int):PlantDescriptionActions
}