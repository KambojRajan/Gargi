package com.darkrai.gargi.presentation.welcome.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.darkrai.gargi.R
import com.darkrai.gargi.presentation.auth.signIn.stateActions.SignInActions
import com.darkrai.gargi.presentation.auth.signIn.stateActions.SignInStates
import com.darkrai.gargi.presentation.auth.signUp.stateActions.States
import com.darkrai.gargi.presentation.navigation.components.Routes
import com.darkrai.gargi.ui.theme.GargiTheme

@Composable
fun WelcomeScreen(
    navHostController: NavHostController,
    onAction: (SignInActions) -> Unit,
    states: SignInStates
) {
    GargiTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
            ) {
                Text(
                    text = "Welcome To",
                    color = Color.Gray,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(60.dp))
                    Text(
                        text = "Gargi",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        letterSpacing = 0.15.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }

                Text(
                    text = "We offer you the best service, and organically handled and developed plants." +
                            " Plants that you can care for, love them as your own child. We are here to make you fall in" +
                            " love with plant parenthood",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = Color.Gray
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Get started", color = Color.Gray, fontSize = 13.sp)
                        IconButton(
                            onClick = {
                                if(states.user != null){
                                    navHostController.navigate(Routes.Home.name)
                                }else{
                                    navHostController.navigate(Routes.SignIn.name)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Start Exploring",
                                modifier = Modifier
                                    .size(25.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(40.dp)
                                    )
                                    .padding(2.dp),
                                tint = MaterialTheme.colorScheme.surface,
                            )
                        }
                    }
                }
            }
            Image(
                painter = painterResource(R.drawable.welcome),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(top = 16.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview
@Composable
fun co() {
    WelcomeScreen(navHostController = rememberNavController(), onAction = {}, states = SignInStates())
}