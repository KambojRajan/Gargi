package com.darkrai.gargi.presentation.dashboard.components

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.darkrai.gargi.R
import com.darkrai.gargi.presentation.auth.signIn.stateActions.SignInStates
import com.darkrai.gargi.presentation.dashboard.stateActions.DashboardActions
import com.darkrai.gargi.presentation.dashboard.stateActions.DashboardStates
import com.darkrai.gargi.presentation.home.components.CategoryCard
import com.darkrai.gargi.ui.theme.GargiTheme

@Composable
fun DashboardScreen(signInStates: SignInStates, dashboardStates: DashboardStates, dashboardActions: (DashboardActions) -> Unit) {

    var addingNewplant by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        dashboardActions(DashboardActions.GetPendingOrders)
    }
    val context = LocalContext.current

    LaunchedEffect(dashboardStates.errorMessage) {
        if (dashboardStates.errorMessage.isNotEmpty()) {
            Toast.makeText(context, dashboardStates.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
    Scaffold(
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
        topBar = {
            Column {
                Box(
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.welcome),
                        contentDescription = "welcome",
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Welcome ${signInStates.user?.username}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        },
        floatingActionButton = {
           Button(
               onClick = {
                   addingNewplant = true
               }
           ) {
               Text(text = "Add plant")
           }

            if(addingNewplant){
                fun onDismiss(){
                    addingNewplant = false
                }
                AddListing(states = dashboardStates, onAction = dashboardActions, onDismiss = {onDismiss()}) {
                    dashboardActions(DashboardActions.AddNewPlant)
                }
            }
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .padding(it),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(dashboardStates.pendingOrders) { order ->
                OrderCard(order = order)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddListing(
    states: DashboardStates,
    onAction: (DashboardActions) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val categories = arrayOf("top seller", "season's choice", "Indoor", "Outdoor", "Vines", "Tropical")
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedCategory by rememberSaveable { mutableStateOf(categories[0]) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            uris.map { uri ->
                onAction(DashboardActions.SetPlantImages(uri))
            }
        }
    )

    GargiTheme {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("Cancel")
                }
            },
            title = {
                Text(
                    text = "Add a New Plant",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp
                )
            },
            text = {
                LazyColumn(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    item{
                        OutlinedTextField(
                            value = states.plantName,
                            onValueChange = {
                                onAction(DashboardActions.SetPlantName(it))
                            },
                            label = { Text("Plant Name") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = states.plantDescription,
                            onValueChange = {
                                onAction(DashboardActions.SetDescription(it))
                            },
                            label = { Text("Description") },
                            modifier = Modifier.fillMaxWidth(),
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            OutlinedTextField(
                                value = selectedCategory,
                                onValueChange = {
                                                onAction(DashboardActions.SetSelectedCategory(it))
                                },
                                readOnly = true,
                                label = { Text("Category") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { expanded = true }
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                categories.forEach { category ->
                                    DropdownMenuItem(
                                        onClick = {
                                            onAction(DashboardActions.SetSelectedCategory(category))
                                            expanded = false
                                        },
                                        text = { Text(text = category) }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = states.quantity.toString(),
                            onValueChange = {
                                it.toIntOrNull()?.let { q ->
                                    onAction(DashboardActions.SetQuantity(q))
                                }
                            },
                            label = { Text("Quantity") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = if (states.plantPrice == 0f) "" else states.plantPrice.toString(),
                            onValueChange = {
                                it.toFloatOrNull()?.let { price ->
                                    onAction(DashboardActions.SetPrice(price))
                                }
                            },
                            label = { Text("Price") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = "No",
                            onValueChange = {
                                if (it == "Yes" || it == "yes") {
                                    onAction(DashboardActions.SetForRescue)
                                }
                            },
                            label = { Text("Put for rescue") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        if(states.plantImages.size > 0){
                            LazyRow(
                                modifier = Modifier
                                    .height(300.dp)
                                    .padding(vertical = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(states.plantImages) { uri ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .aspectRatio(1f)
                                            .shadow(8.dp, shape = RoundedCornerShape(8.dp)),
                                        shape = RoundedCornerShape(8.dp),
                                    ) {
                                        AsyncImage(
                                            model = uri,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                }
                            }
                        }
                        Button(
                            onClick = { photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Pick Images")
                        }
                    }
                }
            }
        )
    }
}
