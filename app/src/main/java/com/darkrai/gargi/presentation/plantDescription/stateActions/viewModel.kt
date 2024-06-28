package com.darkrai.gargi.presentation.plantDescription.stateActions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkrai.gargi.data.models.toPlant
import com.darkrai.gargi.data.repositories.plantRepositoryImplement
import com.darkrai.gargi.presentation.donate.stateActions.DonateStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException
import javax.inject.Inject

@HiltViewModel
class PlantDescriptionViewModel @Inject  constructor(
    private val plantRepo:plantRepositoryImplement
) :ViewModel(){
    private val _state = MutableStateFlow(PlantDescriptionStates())
    val states = _state

    companion object{
        private const val TAG = "PlantDescriptionViewModel"
    }

    fun onAction(actions: PlantDescriptionActions) {
        when (actions) {
            is PlantDescriptionActions.GetPlantById -> {
                try {
                    viewModelScope.launch {
                        val plant = plantRepo.getPlantById(actions.plantId)
                        _state.update {
                            it.copy(
                                selectedPlant = plant?.toPlant()
                            )
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                    if (e is CancellationException) throw e
                }
            }
        }
    }
}