package com.darkrai.gargi.presentation.search.stateActions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkrai.gargi.data.models.toPlant
import com.darkrai.gargi.data.repositories.plantRepositoryImplement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val plantRepo: plantRepositoryImplement
):ViewModel() {
    companion object {
        private const val TAG = "SearchViewModel"
    }

    private val _states = MutableStateFlow(SearchStates())
    val states = _states

    fun onAction(action: SearchActions){
        when(action){
            is SearchActions.ChangeQueryString -> {
                Log.d(TAG, "Query Changed: ${action.query}")
                _states.update {
                    it.copy(
                        query = action.query
                    )
                }
            }
            is SearchActions.ProcessSearch -> {
                try {
                   viewModelScope.launch {
                       val plants = plantRepo.getSearchedPlants(_states.value.query)
                       _states.update {
                           it.copy(
                               searchProduct = plants.map {
                                   it.toPlant()
                               }
                           )
                       }
                   }
                }catch (e:Exception){
                    if(e is CancellationException)throw e
                    Log.e(TAG, e.toString())
                }
            }

            is SearchActions.onActiveChange -> {
                _states.update {
                    it.copy(
                        isSearching = action.state
                    )
                }
            }
        }
    }

}