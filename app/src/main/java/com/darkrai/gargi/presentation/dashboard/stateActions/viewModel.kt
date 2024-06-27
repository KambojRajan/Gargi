package com.darkrai.gargi.presentation.dashboard.stateActions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkrai.gargi.data.models.Plant
import com.darkrai.gargi.data.repositories.DashboardRepositoryImplement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardRepo: DashboardRepositoryImplement
):ViewModel() {
    companion object{
        private const val TAG = "DashboardViewModel"
    }

    private val _states = MutableStateFlow(DashboardStates())
    val states = _states

    fun onAction(action: DashboardActions){
        when(action){
            DashboardActions.GetPendingOrders -> {
                try {
                    viewModelScope.launch {
                        val orders = dashboardRepo.getPendingOrders()
                        _states.update {
                            it.copy(
                                pendingOrders = orders
                            )
                        }
                    }
                }catch (e:Exception){
                    if(e is CancellationException)throw e
                    Log.e(TAG, e.toString())
                }
            }
            DashboardActions.SetForRescue -> {
                _states.update {
                    it.copy(
                        forRescue = true
                    )
                }
            }
            DashboardActions.AddNewPlant -> {
                try {
                    val currentState = _states.value

                    val errorMessage = when {
                        currentState.plantName.isEmpty() -> "Plant name cannot be empty."
                        currentState.plantDescription.isEmpty() -> "Plant description cannot be empty."
                        currentState.plantPrice <= 0 -> "Plant price must be greater than zero."
                        currentState.quantity <= 0 -> "Quantity must be greater than zero."
                        currentState.plantImages.isEmpty() -> "At least one image must be selected."
                        else -> null
                    }

                    if (errorMessage != null) {
                        _states.value = currentState.copy(errorMessage = errorMessage)
                        return
                    }

                    viewModelScope.launch {
                        dashboardRepo.addNewPlant(Plant(
                            name = currentState.plantName,
                            description = currentState.plantDescription,
                            price = currentState.plantPrice,
                            forRescue = currentState.forRescue,
                            donatedBy = null,
                            category = currentState.category,
                            images = currentState.plantImages.map { it.toString() },
                            quantity = currentState.quantity
                        ))
                    }
                } catch (e: Exception) {
                    if (e is CancellationException) throw e
                    Log.e(TAG, e.toString())
                }
            }

            is DashboardActions.SetDescription -> {
                _states.update {
                    it.copy(
                        plantDescription = action.description
                    )
                }
            }
            is DashboardActions.SetPlantName -> {
                _states.update {
                    it.copy(
                        plantName = action.name
                    )
                }
            }
            is DashboardActions.SetPrice -> {
                _states.update {
                    it.copy(
                        plantPrice = action.price
                    )
                }
            }
            is DashboardActions.SetSelectedCategory -> {
                _states.update {
                    it.copy(
                        category = action.cat
                    )
                }
            }
            is DashboardActions.SetPlantImages -> {
                _states.update { currentState ->
                    val updatedImages = currentState.plantImages.toMutableList().apply {
                        add(action.images)
                    }

                    currentState.copy(
                        plantImages = updatedImages
                    )
                }
            }
            is DashboardActions.SetQuantity -> {
                _states.update {
                    it.copy(
                        quantity = action.quantity
                    )
                }
            }

            is DashboardActions.SearchAColumn -> {

            }
        }
    }
}