package com.darkrai.gargi.presentation.donate.stateActions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkrai.gargi.data.repositories.plantRepositoryImplement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DonateViewModel @Inject constructor(
    private val plantRepo: plantRepositoryImplement
):ViewModel() {
    private val _state = MutableStateFlow(DonateStates())
    val states = _state

    companion object{
        private const val TAG = "DonateViewMode"
    }

    fun onAction(action: DonateActions){
        when(action){
            is DonateActions.Donate -> {
                try {
                    viewModelScope.launch {
                        plantRepo.addDonation(_state.value.plantId,action.userId)
                    }
                }catch (e:Exception){
                    Log.e(TAG, e.toString())
                }
            }
            is DonateActions.SetError -> {
                _state.update {
                    it.copy(
                        error = action.err
                    )
                }
            }
            is DonateActions.setPlantId -> {
                _state.update {
                    it.copy(
                        plantId = action.id
                    )
                }
            }
        }
    }
}