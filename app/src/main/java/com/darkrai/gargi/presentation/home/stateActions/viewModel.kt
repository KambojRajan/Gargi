package com.darkrai.gargi.presentation.home.stateActions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkrai.gargi.data.models.toPlant
import com.darkrai.gargi.data.repositories.plantRepositoryImplement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val plantRepo: plantRepositoryImplement
):ViewModel() {
    companion object{
        private const val TAG = "HomeScreenViewModel"
    }
    private val _states = MutableStateFlow(HomeScreenStates())
    val states = _states

    fun onAction(action:HomeScreenActions){
        when(action){
            HomeScreenActions.getTopPlants -> {
                try {
                    viewModelScope.launch {
                        val plants = plantRepo.getTopPlants()
                        _states.update {
                            it.copy(
                                topPlants = plants.map {
                                    it.toPlant()
                                }
                            )
                        }
                    }
                }catch (e:Exception){
                    Log.e(TAG, e.toString())
                    if(e is CancellationException){
                        throw e
                    }
                }
            }
            is HomeScreenActions.setIsTryingToSearch -> {
                _states.update {
                    it.copy(
                        isTryingToSearch = action.state
                    )
                }
            }
            is HomeScreenActions.setSelectedCategory ->{
                _states.update {
                    it.copy(
                        selectedCategory = action.cat
                    )
                }
            }

            HomeScreenActions.searchSelectedCategory -> {
              viewModelScope.launch {
                  val plants = plantRepo.getPlantsByCategory((_states.value.selectedCategory?:"top seller").toString())
                  _states.update {
                      it.copy(
                          topPlants = plants.map {
                              it.toPlant()
                          }
                      )
                  }
              }
            }
        }
    }
}