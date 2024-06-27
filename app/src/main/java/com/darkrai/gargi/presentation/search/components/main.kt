package com.darkrai.gargi.presentation.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.darkrai.gargi.presentation.home.stateActions.HomeScreenActions
import com.darkrai.gargi.presentation.search.stateActions.SearchActions
import com.darkrai.gargi.presentation.search.stateActions.SearchStates

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    states:SearchStates,
    onAction:(SearchActions)->Unit,
    navHostController: NavHostController,
    modifier: Modifier,
    homeScreenActions: (HomeScreenActions)->Unit
) {
    SearchBar(
        modifier=modifier
            .fillMaxWidth()
            .padding(10.dp),
        query = states.query,
        onQueryChange = {
            onAction(SearchActions.ChangeQueryString(it))
            onAction(SearchActions.onActiveChange(true))
        },
        onSearch = {
            onAction(SearchActions.ProcessSearch)
            onAction(SearchActions.ChangeQueryString(it))
        },
        active = states.isSearching,
        onActiveChange = {
            onAction(SearchActions.onActiveChange(it))
        },
        placeholder = { Text(text = "Search a plant..")},
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            if (states.isSearching?:false) {
                Icon(
                    modifier = Modifier.clickable {
                       onAction(SearchActions.onActiveChange(false))
                        onAction(SearchActions.ChangeQueryString(""))
                        homeScreenActions(HomeScreenActions.setIsTryingToSearch(false))

                    },
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = "Close icon"
                )
            }
        }
    ) {
        SearchResult(searchStates = states, navHostController = navHostController)
    }
}