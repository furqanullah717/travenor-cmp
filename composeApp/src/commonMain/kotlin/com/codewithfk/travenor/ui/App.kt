package com.codewithfk.travenor.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codewithfk.travenor.Greeting
import com.codewithfk.travenor.cache.TravenorSession
import com.codewithfk.travenor.ui.feature.login.LoginScreen
import com.codewithfk.travenor.ui.feature.register.RegisterScreen
import com.codewithfk.travenor.ui.navigation.NavRouts
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin


@Composable
@Preview
fun App() {
    MaterialTheme {
        val session: TravenorSession = getKoin().get()
        val currentScreen = remember { mutableStateOf<String?>(null) }
        val coroutineScope = rememberCoroutineScope()
        coroutineScope.launch {
            if (session.getToken() != null) {
                currentScreen.value = NavRouts.Home.route
            } else {
                currentScreen.value = NavRouts.Login.route
            }
        }
        val navController = rememberNavController()
        currentScreen.value?.let {
            NavHost(navController, startDestination = it) {
                composable(NavRouts.Login.route) {
                    LoginScreen(navController)
                }
                composable(NavRouts.Register.route) {
                    RegisterScreen(navController)
                }
                composable(NavRouts.Home.route) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Home")
                    }
                }
                composable(NavRouts.Loading.route) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Text("Loading...")
                    }
                }
            }
        }

    }
}