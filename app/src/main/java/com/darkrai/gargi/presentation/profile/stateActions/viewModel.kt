package com.darkrai.gargi.presentation.profile.stateActions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkrai.gargi.data.repositories.DashboardRepositoryImplement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val orderRepo: DashboardRepositoryImplement
):ViewModel() {
    companion object {
        private const val TAG = "ProfileViewModel"
    }

    private val _states = MutableStateFlow(ProfileStates())
    val states = _states

    fun onAction(actions: ProfileActions){
        when(actions){
            is ProfileActions.GetUserPendingOrders -> {
                try {
                    viewModelScope.launch {
                        val orders = orderRepo.getPendingOrdersForAUser(actions.userId)
                        _states.update {
                            it.copy(
                                pendingOrders = orders
                            )
                        }
                    }
                }catch (e:Exception){
                    if(e is CancellationException) throw e
                    Log.e(TAG, e.toString())
                }
            }

            is ProfileActions.findUseDetails -> {
                try {
                    viewModelScope.launch {
                        val res = orderRepo.searchUserProperties(actions.userId)
                        _states.update {
                            it.copy(
                                totalDonatedPlants = res[1],
                                totalOrders = res[0]
                            )
                        }
                    }
                }catch (e:Exception){
                    if(e is CancellationException) throw e
                    Log.e(TAG, e.toString())
                }
            }
        }
    }
}