package com.darkrai.gargi.presentation.search.stateActions

sealed interface SearchActions {
    data class ChangeQueryString(val query:String):SearchActions
    data object ProcessSearch:SearchActions
    data class onActiveChange(val state:Boolean):SearchActions
}